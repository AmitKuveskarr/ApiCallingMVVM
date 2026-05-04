package com.example.apicallingmvvm.data.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefProvider @Inject constructor(@ApplicationContext context: Context) {
    private val appContext = context.applicationContext

    private val  preference : SharedPreferences
        get() = appContext.getSharedPreferences(appContext.packageName, Context.MODE_PRIVATE)

    // todo - save initial api data
    /*fun saveUserDetails(userDetails: LoginData?) {
        val gson = Gson()
        val json = gson.toJson(userDetails)
        preference.edit().putString(KEY_USER_DETAILS_DATA, json).apply()
        fun getLoginCredentials(): Pair<String?, String?> {
        return Pair("12345", "secret") // Mock credentials
    }
}


    fun getUserDetails(): LoginData {
        val gson = Gson()
        val json = preference.getString(KEY_USER_DETAILS_DATA, null)
        return gson.fromJson(json, LoginData::class.java)
        fun getLoginCredentials(): Pair<String?, String?> {
        return Pair("12345", "secret") // Mock credentials
    }
}
*/
    fun getLoginCredentials(): Pair<String?, String?> {
        return Pair("12345", "secret") // Mock credentials
    }
}
