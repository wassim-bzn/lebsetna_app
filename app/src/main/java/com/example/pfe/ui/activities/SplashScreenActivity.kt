package com.example.pfe.ui.activities

import android.os.Bundle
import com.example.pfe.BaseActivity
import com.example.pfe.R
import com.example.pfe.common.PreferenceHelper
import com.example.pfe.common.Root.Companion.goToLoginActivity
import com.example.pfe.common.Root.Companion.goToMainActivity

class SplashScreenActivity : BaseActivity() {
    private val SPLASH_DISPLAY_LENGTH:Long = 1000
    var tagSplash:String?=null
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

        if(user!=null ) {
            goToMainActivity(this@SplashScreenActivity)
        }
        else{
            goToLoginActivity(this@SplashScreenActivity,"")
        }


    }

}