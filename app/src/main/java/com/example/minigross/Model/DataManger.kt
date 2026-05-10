package com.example.minigross.Model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreManager {
    private val Context.dataStore by preferencesDataStore(name = "userData")

    private val userName = stringPreferencesKey("userName")
    private val userPhone = stringPreferencesKey("userPhone")

    suspend fun saveUser(context: Context, phone:String
    ) {
        context.dataStore.edit { prefs ->
            prefs[userPhone] = phone
        }
    }
    suspend fun getUserName(context: Context): String? {
        return context.dataStore.data.map { it[userName] ?: "" }.first()
    }




    suspend fun getUserPhone(context: Context): String? {
        return context?.dataStore?.data?.map { it[userPhone] ?: "" }?.first()
    }



}