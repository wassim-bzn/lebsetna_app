package com.example.pfe.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.pfe.BaseActivity
import com.example.pfe.Ecran_d_accueil
import com.example.pfe.R
import com.example.pfe.common.InputControl
import com.example.pfe.common.PreferenceHelper
import com.example.pfe.common.Root
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_login)
        textViewLinkRegister.setOnClickListener(View.OnClickListener  {
            //go to register
            Root.goToRegisterActivity(this@LoginActivity,"FromLogin")
        })


    }

    /* la fonction pour la navigation pour ajouter un nouveau user */
    fun goToRegister(view: View) {
        Root.goToLoginActivity(this@LoginActivity, "")
    }
    /* La fonction de login */
    fun login_user(view: View) {
        //vérification si l'email est valide
        if(!InputControl.isValidEmail(ed_email,this@LoginActivity)){
            Toast.makeText(baseContext, "Email Invalid.", Toast.LENGTH_SHORT).show()
            return
        }
        //vérification si le mot de passe est valide
        if(!InputControl.isValidLengthPassword(ed_password,this@LoginActivity)){
            Toast.makeText(baseContext, "Password Invalid.", Toast.LENGTH_SHORT).show()
            return
        }
        else{
            /* instantiation de la class Firebase qui nous représente la fonctionnalité d'authentification dans firebase ] */
            auth = Firebase.auth

            /* l'implémentation de l'event listener pour se connecter  */
            auth.signInWithEmailAndPassword(ed_email.text.toString(), ed_password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // si n'y a pas des problèmes au cours d'authentification
                            /**
                             * après l'authentification avec firebase
                             * navigation pour la page d'acceuil
                             * en passent le champ Email */
                        Log.d("loginResult", "signInWithEmail:success")
                        val intent = Intent(this, Ecran_d_accueil::class.java)
                        intent.putExtra("Useremail", ed_email.text.toString())
                        startActivity(intent)
                    } else {
                        // (l'ajout ne fonctionne pas  expl: user login existe déja ou copure d'internet Etc..)
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        ed_password.text.clear()
                    }
                }

        }

    }


}

