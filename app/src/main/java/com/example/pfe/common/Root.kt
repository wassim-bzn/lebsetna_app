package com.example.pfe.common

import android.app.Activity
import android.content.Intent
import com.example.pfe.Ecran_d_accueil
import com.example.pfe.ui.activities.LoginActivity
import com.example.pfe.ui.activities.RegistrationActivity
import com.example.pfe.ui.activities.SplashScreenActivity


class Root{

    companion object{

        fun goToMainActivity(activity: Activity) {
             val intent = Intent(activity, Ecran_d_accueil::class.java)
             activity.startActivity(intent)
             activity.finishAffinity()
        }
        fun goToSplashScreen(activity: Activity) {
            val intent = Intent(activity, SplashScreenActivity::class.java)
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