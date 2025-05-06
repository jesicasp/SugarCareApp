package com.pa.sugarcare.utility

import android.content.Context
import android.content.SharedPreferences

object TokenStorage {

    private const val PREF_NAME = "auth_prefs"
    private const val ACCESS_TOKEN_KEY = "access_token"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveToken(token: String) {
        if (::sharedPreferences.isInitialized) {
            sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
        } else {
            throw IllegalStateException("TokenStorage not initialized. Call TokenStorage.init(context) in Application class.")
        }
    }

    fun getToken(): String? {
        return if (::sharedPreferences.isInitialized) {
            sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        } else {
            throw IllegalStateException("TokenStorage not initialized. Call TokenStorage.init(context) in Application class.")
        }
    }

    fun clearToken() {
        if (::sharedPreferences.isInitialized) {
            sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
        }
    }
}
