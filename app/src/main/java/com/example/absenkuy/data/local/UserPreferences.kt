package com.example.absenkuy.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val NIM = stringPreferencesKey("nim")

    suspend fun saveUser() {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
        }
    }

    suspend fun saveIsUserLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
        Log.d("UserPreferences", "saveIsUserLoggedIn: $isLoggedIn")
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data
            .map { preferences -> preferences[IS_LOGGED_IN] == true }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    suspend fun saveNIM(nim: String) {
        dataStore.edit { preferences ->
            preferences[NIM] = nim
        }
    }

    fun getUserNIM(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[NIM] }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[ACCESS_TOKEN] }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context.dataStore).also { INSTANCE = it }
            }
        }
    }
}