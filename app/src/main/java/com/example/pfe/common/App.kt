package com.halal.halalproject.common

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.preference.PreferenceManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.pfe.BaseActivity
import com.example.pfe.common.PreferenceHelper

import java.util.*

class App : Application() , LifecycleObserver{

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        var change = ""
       var language="en"

        if (language == "ar") {
            change="ar"
        } else if (language=="en" ) {
            change = "en"
        }else {
            change ="ar"
        }
        BaseActivity.dLocale = Locale(change) //set any locale you want here
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        //App in background
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // App in foreground
    }

}
