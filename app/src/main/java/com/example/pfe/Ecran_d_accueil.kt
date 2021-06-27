package com.example.pfe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pfe.adapter.PostsAdapter
import com.example.pfe.common.Root
import com.example.pfe.model.Favourits
import com.example.pfe.model.Habit
import com.example.pfe.model.UserModel
import com.example.pfe.ui.activities.adding_post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.post_item.*


class Ecran_d_accueil : AppCompatActivity() {


    lateinit var btn_ajout: ImageButton
    lateinit var imageButtonRefrech: ImageButton
    lateinit var btnmyPostsid: ImageButton
    var database = FirebaseDatabase.getInstance()
    var TAG: String = "gettingDataFromDb"
    var UserData = UserModel("", "", "", "", "", "", "")
    var list = mutableListOf<Habit>()
    var list_myPosts = mutableListOf<Habit>()
    var list_myPosts_favourits = mutableListOf<Habit>()
    var firstLogin=0
    var userPhone=""
    var action=""
    var userVille=""
    var Useremail=""
    var USER_ROLE=""
    var overrided_Login_number="False"
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ecran_d_accueil)
        listView= findViewById<ListView>(R.id.RecyclerView_Acc)
        imageButtonRefrech= findViewById(R.id.imageButtonRefrech)
        btn_ajout= findViewById(R.id.btn_ajout_post)
        btnmyPostsid= findViewById(R.id.btnmyPostsid)
        /*recevoir de la variable email a partir de la page de login */
        var bundle: Bundle? = intent.extras
        if (bundle!!.getString("Useremail")!=null){
        Useremail = bundle!!.getString("Useremail").toString()
        }
        /*dans le cas de login on ne passe pas les variables phone, role car on va le récuprer à partir de la base de donnée (myRefUser.addValueEventListener)
        * mais si on va y aller à la page add poste qu'elle est une autre activity on va tout d'abord envoyer ces donner (phone , Role ..)
        * et quand on va retourner à la cette page d'accueil voici le code pour les récupérer les donner (phone , Role ..)
        * ainsi qu'il y a le controle (if) pour vérifier si les données viens de la page d'accueil ou bien de l'ajout d'un poste
        *
        * firstlogin =1 cet a dire si je suis connecté pour la 1ere fois a la page d'accueil: donc je vais faire un appel à la base de donnée pour importer tous les données
        * firstlogin =2 j'ai déja les donnée mais s'il ya un nouvelle post(habit) qui a été ajouté,supprimé par un autre user par example on va pas faire un refrech automatiaue on va afficher un boutton de refrech pour nous dire qu'il ya un noueau changement dans la liste
        * firstlogin =3 si le user est dans une autre activité d'ajout d'un habit; list favourits , list my posts; par example et ilya un changement l'application va just nous dire qu'il ya un changement sans affichage du boutton refrech car on est pas à la page d'accueil
        * overrided_Login_number=True c'est une cas lorsque le user se connecte pour la 1ere fois et il va sur une autre page d'ajout habit par example le listener il va détecter qu'on est dans firstlogin=2 ou bien firstlogin=3 donc dans ce cas il va pas afficher les données de nouveau
        * donc overrided_Login_number =True nous informe qu'il faux faire un import ded données de nouveaux lorsqu'on est pas dans firstlogin=1 (on n'est pas connecté pour la 1ere fois)
        *  */
        userVille = bundle!!.getString("userVille").toString()
        if (bundle!!.getString("userPhone")!=null){
            userPhone = bundle!!.getString("userPhone").toString()
            USER_ROLE = bundle!!.getString("userRole").toString()
            overrided_Login_number="True"
        }
        firstLogin=1
        Toast.makeText(this@Ecran_d_accueil, Useremail, Toast.LENGTH_LONG).show()

        /* myRefUser représente le clé "User" ( ou bien la table user** car dans la une base noSql comme FIREBASE on n'a pas la notion des tables plutôt des clés-Valeurs)  */
        /* myRefHabit représente le clé "Habit" ( ou bien la table Habit** car dans la une base noSql comme FIREBASE on n'a pas la notion des tables plutôt des clés-Valeurs)  */

        val myRefHabit = database.getReference("habits")
        val myRefUser = database.getReference("Users")
        imageButtonRefrech.visibility = View.INVISIBLE;
        /*récupération des données de User */
        myRefUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                /**
                 * DataSnapshot = tout les données sous la table (User)
                 * sous le boucle for il ya le champ snapshot
                 * snapshot = chaque user un par un
                 * snapshot.child = les donnée sous le champ snapshot (id,email,firstname ... )
                 * get_value() = c'est une fonction prédéfinie pour récuperer ce donner
                 * toString() = pour convertir ce donné pour string
                 * */
                val UserDatasnap = dataSnapshot.getValue()
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("email").getValue() == Useremail) {
                        UserData.id = snapshot.child("id").getValue().toString()
                        UserData.email = snapshot.child("email").getValue().toString()
                        UserData.firstName = snapshot.child("firstName").getValue().toString()
                        UserData.lastName = snapshot.child("lastName").getValue().toString()
                        UserData.phone = snapshot.child("phone").getValue().toString()
                        UserData.ville = snapshot.child("ville").getValue().toString()
                        UserData.role = snapshot.child("role").getValue().toString()

                        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list,userPhone,action)
                        userPhone=UserData.phone
                        userVille=UserData.ville
                        USER_ROLE=UserData.role
                        imageButtonRefrech.visibility = View.INVISIBLE;
                        if (USER_ROLE=="Professional"){
                             btn_ajout.visibility=View.VISIBLE
                            btnmyPostsid.visibility=View.VISIBLE
                        }else if (USER_ROLE=="Client"){
                            btn_ajout.visibility=View.INVISIBLE
                            btnmyPostsid.visibility=View.INVISIBLE
                        }
                        firstLogin=2
                        break
                    }

              }
                Log.d(TAG, "Value is: $UserDatasnap")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read User Data.", error.toException())
            }
             })
        if (USER_ROLE=="Professional"){
            btn_ajout.visibility=View.VISIBLE
            btnmyPostsid.visibility=View.VISIBLE
        }else if (USER_ROLE=="Client"){
            btn_ajout.visibility=View.INVISIBLE
            btnmyPostsid.visibility=View.INVISIBLE
        }
        firstLogin=2
        myRefHabit.addValueEventListener(object : ValueEventListener {
            /**
             * DataSnapshot = tout les données sous la table (Habit)
             * sous le boucle for il ya le champ snapshot
             * snapshot = chaque Habit un par un
             * snapshot.child = les donnée sous le champ snapshot (name,description,imageUrl ... )
             * get_value() = c'est une fonction prédéfinie pour récuperer ce donnée
             * toString() = pour convertir ce donné pour string
             * */
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = mutableListOf<Habit>()
                val UserDatasnap = dataSnapshot.getValue()
                for (snapshot in dataSnapshot.children) {
                    var Name = snapshot.child("name").getValue().toString()
                    var description = snapshot.child("description").getValue().toString()
                    var ImageUrl = snapshot.child("imageUrl").getValue().toString()
                    var userId = snapshot.child("userId").getValue().toString()
                    var userPhone = snapshot.child("userPhone").getValue().toString()
                    /**
                     * on va créer une liste des habits pour qu'on puisse les afiicher dans la listview (page d'accueil)*/
                    list.add(Habit(Name, description, userId, ImageUrl, userPhone))


                }
            /** firstlogin =1 cet a dire si je suis connecté pour la 1ere fois a la page d'accueil: donc je vais faire un appel à la base de donnée pour importer tous les données
             * firstlogin =2 j'ai déja les donnée mais s'il ya un nouvelle post(habit) qui a été ajouté,supprimé par un autre user par example on va pas faire un refrech automatiaue on va afficher un boutton de refrech pour nous dire qu'il ya un noueau changement dans la liste
             * firstlogin =3 si le user est dans une autre activité d'ajout d'un habit par example et ilya un changement l'application va just nous dire qu'il ya un changement sans affichage du boutton refrech car on est pas à la page d'accueil
            */
                if (firstLogin==1 || overrided_Login_number=="True"){
                    //post adapter= c'est une class pour afficher les données qu'on a récupérer de la base
                    listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list,userPhone,action)
                    imageButtonRefrech.visibility = View.INVISIBLE;
                    firstLogin=2
                    overrided_Login_number="False"
                }else if (firstLogin==2){
                    imageButtonRefrech.visibility = View.VISIBLE;
                    Toast.makeText(this@Ecran_d_accueil, "Habit data list has been changed ! refrech to see the latest posts ", Toast.LENGTH_LONG).show()

                }
                else if (firstLogin==3){
                    Toast.makeText(this@Ecran_d_accueil, "Habit data list has been changed ! back to menu if you want to see the latest posts ", Toast.LENGTH_LONG).show()
                    imageButtonRefrech.visibility = View.INVISIBLE;
                }
                Log.d(TAG, "Value is: $UserDatasnap")
            }
//
            override fun onCancelled(error: DatabaseError) {
                // erreur lors de récupération des données
                Log.w(TAG, "Failed to read habit Data.", error.toException())
            }
        })

        /*instentiation de la base Favourits*/
        val myRefFavorits = database.getReference("Favourits")
        /**
         * DataSnapshot = tout les données sous la table (Favourits)
         * sous le boucle for il ya le champ snapshot
         * snapshot = chaque Favourit un par un
         * snapshot.child = les donnée sous le champ snapshot (habitId, UserPhone... )
         * habitId = c'est l'ID de l'habit et le UserPhone c'est l'identifient unique de user qui a mis l'habit dans sa liste de favourits
         * get_value() = c'est une fonction prédéfinie pour récuperer ce donnée
         * toString() = pour convertir ce donné pour string
         * */
        myRefFavorits.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list_myPosts_favourits = mutableListOf<Habit>()
                val UserDatasnap = dataSnapshot.getValue()
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("userPhone").getValue() == userPhone) {
                        var habitId = snapshot.child("habitId").getValue().toString()
                        var userPhonefav = snapshot.child("userPhone").getValue().toString()
                        for (fav in list){
                            /**
                             * c'est un controle sur tous les champs pour récupérer juste les habit favorisé par le user connecté*/
                            if (fav.userId==habitId&& userPhone==userPhonefav ){
                                    list_myPosts_favourits.add(fav)
                            }

                        }

                    }

                }


                Log.d(TAG, "Value is: $UserDatasnap")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read User Data.", error.toException())
            }
        })


    }


    /**
     * la fonction de boutton click pour naviger ver la page d'ajout d'un habit passent les valeurs
     * userPhone
     * Useremail
     * userVille
     * USER_ROLE
     * pour ne pas perdre les valeurs de ces champs et pour éviter une appele à la base de donnée pour diminuer le charge sur la base !
     * */
    fun btn_ajout_function(view: View) {
        val myToast = Toast.makeText(applicationContext, "btn is clicked", Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT, 200, 200)
        myToast.show()
        val intent = Intent(this, adding_post::class.java)
        intent.putExtra("userPhone", userPhone)
        intent.putExtra("Useremail", Useremail)
        intent.putExtra("userVille", userVille)
        intent.putExtra("userRole",USER_ROLE)
        startActivity(intent)
    }
    /**
     * boutton click pour refrech le menu d'ecran d'accueil
     * imageButtonRefrech pour INVISIBLE == car n'est pas logique de faire un refrech sur tous les donnée en restent le boutton refrech affiché*/
    fun back_toMenu(view: View) {
        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list,userPhone,"")
        imageButtonRefrech.visibility = View.INVISIBLE;
    }

    /**
     * fonction pour afficher mes habits dans lesquels j'ai fait un upload
     *
     * */
    fun btnmyPosts(view: View) {
        list_myPosts = mutableListOf<Habit>()
        action="viewMyPosts"
        firstLogin=3
        for (habit in list){
            /**controle pour extracter just les posts (habits) de user connecté*/
            if (habit.userPhone==userPhone){
                list_myPosts.add(habit)
            }

        }
        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list_myPosts,userPhone,action)

        imageButtonRefrech.visibility = View.INVISIBLE;
    }
    /* buttoon clicl pour l'affichage de la liste des favourits */
    fun favouritbtn(view: View) {

        firstLogin=3

        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list_myPosts_favourits,userPhone,"")

        imageButtonRefrech.visibility = View.INVISIBLE;
    }
    /*boutton click pour faire un refrech des donner si quelqun des users connectés uploqd un autre post ou bien le supprimer*/
    fun refrechData(view: View) {
        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list,userPhone,action)
        imageButtonRefrech.visibility = View.INVISIBLE;
    }
    /*boutton click pour la décoonnection */
    fun logout_user(view: View) {
        /*fonction de firebqse pour la déconnection */
        Firebase.auth.signOut()
        /*retourner à la page de login*/
        Root.goToLoginActivity(this@Ecran_d_accueil, "signout")
    }

}