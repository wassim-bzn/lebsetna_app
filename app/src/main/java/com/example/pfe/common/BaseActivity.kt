package com.example.pfe

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.Serializable
import java.util.*

open class BaseActivity : AppCompatActivity(),Serializable {
    companion object {
         var dLocale: Locale? = null
        var isConnected: Boolean = true
        var activity:Activity?=null
        var isAppRunning = false
    }
    var TAG = this.javaClass.simpleName
     var oldLocale: Locale? = null
    init {
        updateConfig(this)
        isAppRunning=true
    }
    fun updateConfig(wrapper: ContextThemeWrapper) {
        if (dLocale==null){
            dLocale= Locale("en")
        }
        if(dLocale==Locale("") ) // Do nothing if dLocale is null
            return
        oldLocale= dLocale
        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity=this
        isAppRunning=true
        hideKeyboard()
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
        changeStatusBarColor(R.color.transparent)
    }
    fun changeStatusBarColor(color: Int) {
        val window: Window = this.getWindow()

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, color))
        }
        val winParams = window.attributes
        winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    open fun hideStatusBar() {
        val view = window.decorView
        val uiOption = View.SYSTEM_UI_FLAG_FULLSCREEN
        view.systemUiVisibility = uiOption
        val actionBar: ActionBar? = actionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun Activity.hideKeyboard() {
        hideKeyboard((if (currentFocus == null) View(this) else currentFocus)!!)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onStop() {
        hideKeyboard()
        super.onStop()
        isAppRunning=false

    }

    open fun restartApp() {
        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(i)
        oldLocale=null
        dLocale=null

    }

    override fun onStart() {
        hideKeyboard()
        super.onStart()
        isAppRunning=true
    }

    fun onclickBack(view: View){
        finish()
    }


    override fun onResume() {
        super.onResume()
        if (oldLocale!= dLocale){
            recreate()
        }
        isAppRunning=true
    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning=false
    }




}
