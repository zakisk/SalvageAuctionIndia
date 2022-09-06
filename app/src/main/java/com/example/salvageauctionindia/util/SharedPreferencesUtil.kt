package com.example.salvageauctionindia.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.salvageauctionindia.util.Constants.PREF_NAME
import com.example.salvageauctionindia.util.Constants.USER_CITY
import com.example.salvageauctionindia.util.Constants.USER_MOBILE
import com.example.salvageauctionindia.util.Constants.USER_NAME
import com.example.salvageauctionindia.util.Constants.USER_STATE

class SharedPreferencesUtil(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun savePreferences(name: String, mobile: String, city: String, state: String) {
        put(USER_NAME, name)
        put(USER_MOBILE, mobile)
        put(USER_CITY, city)
        put(USER_STATE, state)
    }


    @Suppress("UNCHECKED_CAST")
    fun <T : Any?> get(key: String): T? {
        return pref.all.getOrDefault(key, null) as T
    }


    fun <T> put(key: String, value: T) {
        pref.edit {
            when (value) {
                is String -> putString(key, value).apply()
                is Int -> putInt(key, value).apply()
                is Boolean -> putBoolean(key, value).apply()
            }
        }
    }

//    fun delete(key: String) {
//        pref.all.remove(key)
//    }
}