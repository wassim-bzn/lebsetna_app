package com.example.pfe.common


import android.content.Context
import android.content.SharedPreferences
import com.example.pfe.model.UserModel
import com.google.gson.Gson


class PreferenceHelper {

    companion object {
        /**
         * Preference Const
         */

        private var app_preferences: SharedPreferences? = null
        val PREF_NAME = "Halal"
        private val USER = "User"
        private val IsLogIn="IsLogIn"

        fun putIsLogIn(code:Boolean,context: Context) {
            app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val edit: SharedPreferences.Editor = app_preferences!!.edit()
            edit.putBoolean(IsLogIn, code)
            edit.commit()
        }
        fun getIsLogInValue(context: Context): Boolean {
            app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return app_preferences!!.getBoolean(IsLogIn,false)
        }
        fun saveUser(context: Context,user: UserModel?) {
            app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val prefsEditor: SharedPreferences.Editor = app_preferences!!.edit()
            var gson = Gson()
            var json = gson.toJson(user)
            prefsEditor.putString(USER, json)
            prefsEditor.commit()


        }
        fun getUser(context: Context): UserModel? {
                var user:UserModel?=null
        app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json: String = app_preferences!!.getString(USER, "")!!
                val gson = Gson()
        user = gson.fromJson(json, UserModel::class.java)
        if(user!=null){
            return user
        }
        else{
            return null
        }
        }



        fun clearSharedPreference() {
            val editor: SharedPreferences.Editor = app_preferences!!.edit()
            editor.clear()
            editor.apply()
        }
        fun getStringFromSharedPreferences(KEY_NAME: String,context: Context):String?{
                app_preferences = context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                )
        return app_preferences!!.getString(KEY_NAME, "")!!
        }
        fun getIntFromSharedPreferences(KEY_NAME: String,context: Context):Int{
            app_preferences = context.getSharedPreferences(
                    PREF_NAME,
                    Context.MODE_PRIVATE
            )
            return app_preferences!!.getInt(KEY_NAME, 0)!!
        }
        fun saveToSharedPreferences(KEY_NAME: String, value: Any,context: Context){
            app_preferences = context.getSharedPreferences(
                    PREF_NAME,
                    Context.MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = app_preferences!!.edit()
            if (value is Int){
                editor.putInt(KEY_NAME, value as Int)
            }
            else if(value is String){
                editor.putString(KEY_NAME, value as String)
            }
            else if(value is Boolean){
                editor.putBoolean(KEY_NAME, value as Boolean)
            }
            editor.commit()
        }

    }
}