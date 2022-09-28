package com.example.mockvakotlin.sharedpref

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun getSessionId(): String? {
        return sharedPref.getString(Constant.SESSION_ID, null)
    }

    fun setSessionId(sessionId: String) {
        editor.putString(Constant.SESSION_ID, sessionId)
            .apply()
    }

    fun getAccountId(): String? {
        return sharedPref.getString(Constant.ACCOUNT_ID, null)
    }

    fun setAccountId(accountId: String) {
        editor.putString(Constant.ACCOUNT_ID, accountId)
            .apply()
    }

    fun getStatusLogin():Boolean{
        return sharedPref.getBoolean(Constant.IS_LOGIN,false)
    }

    fun setStatusLogin(statusLogin: Boolean){
        editor.putBoolean(Constant.IS_LOGIN,statusLogin)
            .apply()
    }

    fun clear() {
        editor.clear()
            .apply()
    }

}