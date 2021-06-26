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
        var bundle: Bundle? = intent.extras
        if (bundle!!.getString("Useremail")!=null){
        Useremail = bundle!!.getString("Useremail").toString()
        }
        userVille = bundle!!.getString("userVille").toString()
        if (bundle!!.getString("userPhone")!=null){
            userPhone = bundle!!.getString("userPhone").toString()
            USER_ROLE = bundle!!.getString("userRole").toString()
            overrided_Login_number="True"
        }
        firstLogin=1
        Toast.makeText(this@Ecran_d_accueil, Useremail, Toast.LENGTH_LONG).show()
        val myRefHabit = database.getReference("habits")
        val myRefUser = database.getReference("Users")
        imageButtonRefrech.visibility = View.INVISIBLE;

        myRefUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
                    list.add(Habit(Name, description, userId, ImageUrl, userPhone))


                }
                if (firstLogin==1 || overrided_Login_number=="True"){
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
                // Failed to read value
                Log.w(TAG, "Failed to read habit Data.", error.toException())
            }
        })


        val myRefFavorits = database.getReference("Favourits")

        myRefFavorits.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list_myPosts_favourits = mutableListOf<Habit>()
                val UserDatasnap = dataSnapshot.getValue()
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.child("userPhone").getValue() == userPhone) {
                        var habitId = snapshot.child("habitId").getValue().toString()
                        var userPhonefav = snapshot.child("userPhone").getValue().toString()
                        for (fav in list){

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

    fun back_toMenu(view: View) {
        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list,userPhone,"")
        imageButtonRefrech.visibility = View.INVISIBLE;
    }

    fun btnmyPosts(view: View) {
        list_myPosts = mutableListOf<Habit>()
        action="viewMyPosts"
        firstLogin=3
        for (habit in list){

            if (habit.userPhone==userPhone){
                list_myPosts.add(habit)
            }

        }
        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list_myPosts,userPhone,action)

        imageButtonRefrech.visibility = View.INVISIBLE;
    }

    fun favouritbtn(view: View) {

        firstLogin=3

        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list_myPosts_favourits,userPhone,"")

        imageButtonRefrech.visibility = View.INVISIBLE;
    }
    fun refrechData(view: View) {
        listView.adapter = PostsAdapter(this@Ecran_d_accueil, R.layout.post_item, list,userPhone,action)
        imageButtonRefrech.visibility = View.INVISIBLE;
    }
    fun logout_user(view: View) {
        /*fonction de firebqse pour la déconnection */
        Firebase.auth.signOut()
        /*retourner à la page de login*/
        Root.goToLoginActivity(this@Ecran_d_accueil, "signout")
    }

}