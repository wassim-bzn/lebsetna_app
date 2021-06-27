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

    }
}