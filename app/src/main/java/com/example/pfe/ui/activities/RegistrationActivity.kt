package com.example.pfe.ui.activities

import android.app.ProgressDialog
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
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class RegistrationActivity() : AppCompatActivity() {
    private lateinit var inputValidation: InputControl
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

    fun back_to_login(view: View) {
        Root.goToLoginActivity(this@RegistrationActivity, "")
    }
    fun register_user(view: View) {
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
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users")
        var uuid:String= edt_telephone.text.toString()
        val UserModel = UserModel(uuid,
            edt_email.text.toString(),
            edt_name.text.toString(),
            edt_prenom.text.toString(),
            edt_telephone.text.toString(),
            ville,
            selectedradiovalue)

        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(edt_email.text.toString(), edt_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("ResultAuth", "createUserWithEmail:success")
                    val user = auth.currentUser
                    myRef.child(UserModel.id).setValue(UserModel).addOnSuccessListener {

                        Toast.makeText(this,"Successfully Saved User ", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Ecran_d_accueil::class.java)
                        intent.putExtra("Useremail", edt_email.text.toString())


                        startActivity(intent)

                    }.addOnFailureListener{
                        Toast.makeText(this,"Failed To save User! ", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("ResultAuth", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    ed_password.text.clear()
                }
            }




    }

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
