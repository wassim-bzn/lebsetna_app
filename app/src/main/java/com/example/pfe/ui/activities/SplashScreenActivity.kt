package com.example.pfe.ui.activities

import android.os.Bundle
import com.example.pfe.BaseActivity
import com.example.pfe.R
import com.example.pfe.common.PreferenceHelper
import com.example.pfe.common.Root.Companion.goToLoginActivity
import com.example.pfe.common.Root.Companion.goToMainActivity
/**
 * c'est un classe qu'elle va etre appeler au démarrage de l'applciation */
class SplashScreenActivity : BaseActivity() {
    private val SPLASH_DISPLAY_LENGTH:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_splash_screen)
        Thread(Runnable {
            doWork()
            startApp()
            finish()
        }).start()
    }
    private fun doWork() {
        var progress = 0
        while (progress < 100) {
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progress += 60
        }
    }
    fun startApp() {
        //notification on background
        var user= PreferenceHelper.getUser(this)
        /**
         * si le user est déja connecté il faux le ramener à la page de d'accueil  */
        if(user!=null ) {
            goToMainActivity(this@SplashScreenActivity)
        }
        else{
            /**
             * si le user n'est pas connecté il faux le ramener à la page de login  */
            goToLoginActivity(this@SplashScreenActivity,"")
        }


    }

}