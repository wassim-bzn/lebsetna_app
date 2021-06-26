package com.example.pfe.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.pfe.Ecran_d_accueil
import com.example.pfe.R
import com.example.pfe.common.InputControl
import com.example.pfe.common.Root
import com.example.pfe.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class RegistrationActivity() : AppCompatActivity() {
    /** les variables qui contient les champs a remplir */
    private lateinit var btn_Inscription: AppCompatButton
    private lateinit var edt_name:EditText
    private lateinit var edt_prenom:EditText
    private lateinit var edt_telephone:EditText
    private lateinit var ville_spinner_list:Spinner
    private lateinit var edt_email:EditText
    private lateinit var radiogroupe:RadioGroup
    private lateinit var rd_cliet:RadioButton
    private lateinit var rd_professionel:RadioButton
    private lateinit var edt_password:EditText
    var selectedradiovalue=""
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        /* liste déroulante qui contient les villes*/
        ville_spinner_list = findViewById(R.id.ville_spinner_list)
        val adapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.country,
                android.R.layout.simple_spinner_item
            )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        ville_spinner_list.setAdapter(adapter)

    }
    /**
     * fonction pour nous ramener à la page de login
     * c'est une click sur un text
    */
    fun back_to_login(view: View) {
        Root.goToLoginActivity(this@RegistrationActivity, "")
    }

    /**
     * fonction pour nous ajouter un user dans la base
     * après le sauvgarde de user elle nous ramener à la pager d'acceuil ]
     *
     */
    fun register_user(view: View) {
        /** les variables qui contient les champs a remplir */
        edt_name = findViewById(R.id.edt_Name)as EditText
        edt_prenom = findViewById(R.id.edt_Prenom)as EditText
        edt_telephone = findViewById(R.id.edt_Tele)as EditText
        edt_email = findViewById(R.id.Edt_Email)as EditText
        radiogroupe = findViewById(R.id.radio_groupe) as RadioGroup
        rd_cliet = findViewById(R.id.radio_client) as RadioButton
        rd_professionel = findViewById(R.id.radio_professionnel) as RadioButton
        edt_password = findViewById(R.id.edt_Password)as EditText
        btn_Inscription = findViewById(R.id.Btn_Inscription)
        val ville: String = ville_spinner_list.getSelectedItem().toString()
        /**
         * instantiation de la class Firebase qui nous représente la base de donnée realtime dans Firebase ]
         */
        val database = FirebaseDatabase.getInstance()
        /* myRef représente le clé "User" ( ou bien la table user** car dans la une base noSql comme FIREBASE on n'a pas la notion des tables plutôt des clés-Valeurs)  */
        val myRef = database.getReference("Users")
        /* le champ uuid el va représente un identifient uniaue pour un user qui sera le numéro de téléphone*/
        var uuid:String= edt_telephone.text.toString()
        /* instantiation de la classe userModel qu'elle va contenir tous les infos de user à partir des inputs */
        val UserModel = UserModel(uuid,
            edt_email.text.toString(),
            edt_name.text.toString(),
            edt_prenom.text.toString(),
            edt_telephone.text.toString(),
            ville,
            selectedradiovalue)

        /* instantiation de la class Firebase qui nous représente la fonctionnalité d'authentification dans firebase ] */
        auth = Firebase.auth

        /* l'implémentation de l'event listener pour ajouter les donnée de l'authentification  */
        auth.createUserWithEmailAndPassword(edt_email.text.toString(), edt_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // S'il n' y a pas des problème dans le login
                    Log.d("ResultAuth", "createUserWithEmail:success")
                    /**
                     * après l'enregistement de user dans la base d'authentification dans firebase avec le champ Email en commun
                     * voici l'ajout des données relier au user dans la base de donnée real time
                     * la variable myref==> USERS
                     *                       myref.child    ==> Id
                     *                                           myref.child.setValue ==> la valeur de l'ID
                      */

                    myRef.child(UserModel.id).setValue(UserModel).addOnSuccessListener {

                        Toast.makeText(this,"Successfully Saved User ", Toast.LENGTH_SHORT).show()
                        /* après avoir enregistrer les données dans la base de donnée on vas y aller à la page d'acceul avec en passent le champ email
                        *pour justement récuperer les donner de user à partir de ce champ la  */
                        val intent = Intent(this, Ecran_d_accueil::class.java)
                        intent.putExtra("Useremail", edt_email.text.toString())
                        startActivity(intent)

                    }.addOnFailureListener{
                        // (l'ajout de user dans la base de donnée real time n'a pas fonctionné   expl:copure d'internet,Etc.. )
                        Toast.makeText(this,"Failed To save User! ", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // (l'ajout ne fonctionne pas  expl: user login existe déja ou copure d'internet Etc..)
                    Log.w("ResultAuth", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    ed_password.text.clear()
                }
            }




    }
 /*
 * c'est une fonction qui gère les bouttons radio de client ou Professional */
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_client ->
                    if (checked) {
                        selectedradiovalue="Client"
                    }
                R.id.radio_professionnel ->
                    if (checked) {
                        selectedradiovalue="Professional"
                    }
            }
        }
    }

}





//    btn_Inscription.setOnClickListener {
//        val contentValues=ContentValues()
//        if (rd_cliet.isChecked) {
//            contentValues.put(DatabaseHelper.COLUMN_CLIENT_NAME, edt_name.text.toString())
//            contentValues.put(DatabaseHelper.COLUMN_CLIENT_PRENOM,
//                edt_prenom.text.toString()
//            )
//            contentValues.put(DatabaseHelper.COLUMN_CLIENT_VILLE, spinner.toString())
//            contentValues.put(
//                DatabaseHelper.COLUMN_CLIENT_TELEPHONE,
//                edt_telephone.text.toString()
//            )
//            contentValues.put(DatabaseHelper.COLUMN_CLIENT_EMAIL, edt_email.text.toString())
//            contentValues.put(
//                DatabaseHelper.COLUMN_CLIENT_PASSWORD,
//                edt_password.text.toString()
//            )

//        }
//        else if (rd_professionel.isChecked) {
//            contentValues.put(DatabaseHelper.COLUMN_PROFESSIONNEL_NAME, edt_name.text.toString())
//            contentValues.put(DatabaseHelper.COLUMN_PROFESSIONNEL_PRENOM,
//                edt_prenom.text.toString()
//            )
//            contentValues.put(DatabaseHelper.COLUMN_PROFESSIONNEL_VILLE, spinner.toString())
//            contentValues.put(
//    }
