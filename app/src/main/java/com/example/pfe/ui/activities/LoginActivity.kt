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


    fun goToRegister(view: View) {
        Root.goToLoginActivity(this@LoginActivity, "")
    }

    fun login_user(view: View) {
        //verif if email is valid
        if(!InputControl.isValidEmail(ed_email,this@LoginActivity)){
            return
        }
        //verif if password is valid
        if(!InputControl.isValidLengthPassword(ed_password,this@LoginActivity)){
            return
        }
        else{
            // set user is logged In in the shared
            PreferenceHelper.putIsLogIn(true,this@LoginActivity)
            //open Main
            // ...
            auth = Firebase.auth

            auth.signInWithEmailAndPassword(ed_email.text.toString(), ed_password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("loginResult", "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, Ecran_d_accueil::class.java)
                        intent.putExtra("Useremail", ed_email.text.toString())
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        ed_password.text.clear()
                    }
                }

        }

    }


}

