package com.example.mockvakotlin.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptSharedPref(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val fileName = "${context.packageName}${Constant.PREF_NAME}"
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        fileName,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val edit: SharedPreferences.Editor by lazy { sharedPreferences.edit() }

    fun setSessionId(sessionId: String) {
        edit.putString(Constant.SESSION_ID, sessionId).commit()
    }

    fun getSessionId(): String? {
        return sharedPreferences.getString(Constant.SESSION_ID, null)
    }

    fun setAccountId(sessionId: String) {
        edit.putString(Constant.ACCOUNT_ID, sessionId).commit()
    }

    fun getAccountId(): String? {
        return sharedPreferences.getString(Constant.ACCOUNT_ID, null)
    }

    fun clearEncryptData(){
        edit.clear()
        edit.commit()
    }
}