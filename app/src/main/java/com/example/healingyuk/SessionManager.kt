package com.example.healingyuk

import android.content.Context
import com.google.gson.Gson

object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_USER = "user_data"

    fun saveUser(context: Context, user: User) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val userJson = Gson().toJson(user)
        editor.putString(KEY_USER, userJson)
        editor.apply()
    }

    fun getUser(context: Context): User? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userJson = sharedPref.getString(KEY_USER, null)
        return if (userJson != null) Gson().fromJson(userJson, User::class.java) else null
    }

    fun clearUser(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }
}
