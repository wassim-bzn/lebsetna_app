package com.example.pfe.common

import android.app.Activity
import android.content.Intent
import com.example.pfe.Ecran_d_accueil
import com.example.pfe.ui.activities.LoginActivity
import com.example.pfe.ui.activities.RegistrationActivity
import com.example.pfe.ui.activities.SplashScreenActivity

/**
 * c'est une class qui contient des fonctions pour faire la navigation entre les activities (pages) */
class Root{
    /** j'ai utilisé cette classe d'objet qu'elle ne sera pas appelé ou bien instentier q'une seule fois
     * ( un object dans kotlin in ne peux pas etre instentier qu'une seule fois )
     * parce que logiquement on vas naviger d'une activité pour un autre  !
     * donc on n'a pas besoin de faire l'instentiation de cette class plus qu'un seule fois
     */
    companion object{

        fun goToMainActivity(activity: Activity) {
             val intent = Intent(activity, Ecran_d_accueil::class.java)
             activity.startActivity(intent)
             activity.finishAffinity()
        }

        fun goToLoginActivity(activity: Activity, tag: String) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.putExtra("tag", tag)
            activity.startActivity(intent)

        }

        fun goToRegisterActivity(activity: Activity, tag: String) {
            val intent = Intent(activity, RegistrationActivity::class.java)
            intent.putExtra("tag", tag)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}