package com.openmeet.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * This class is used to store the user credentials in an encrypted way
 *
 * @author Yuri Brandi
 */
class UserEncryptedData(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val PREFERENCES_FILE_NAME = "com.openmeet.user_encrypted_preferences"

    init {
        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            PREFERENCES_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun storeCredentials(email: String, password: String) {
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString("email", email)
        sharedPreferencesEditor.putString("pwd", password)
        sharedPreferencesEditor.apply()
    }

    fun storePrivateKey(){
    }

    fun getAllAsHashMap(): HashMap<String, String?> {
        val retValues = HashMap<String, String?>()
        retValues["email"] = sharedPreferences.getString("email", null)
        retValues["pwd"] = sharedPreferences.getString("pwd", null)
        retValues["PK"] = sharedPreferences.getString("PK", null)
        return retValues
    }

    fun deleteAllValues(){
        sharedPreferences.edit().clear().apply()
    }
}