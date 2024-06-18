package com.meone.montir.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.userId
            preferences[NAME_KEY] = user.name
            preferences[TOKEN_KEY] = user.token
            preferences[GENDER_KEY] = user.gender
            preferences[AGE_KEY] = user.age
            preferences[CITY_KEY] = user.city
            preferences[WEIGHT_KEY] = user.weight
            preferences[HEIGHT_KEY] = user.height
            preferences[BMI_KEY] = user.bmi as Int
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USER_ID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[GENDER_KEY] ?: 0,
                preferences[CITY_KEY] ?: "",
                preferences[WEIGHT_KEY] ?: 0,
                preferences[HEIGHT_KEY] ?: 0,
                preferences[AGE_KEY] ?: 0,
                preferences[BMI_KEY] ?: 0,
                preferences[IS_LOGIN_KEY] ?: false,
            )
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val GENDER_KEY = intPreferencesKey("gender")
        private val AGE_KEY = intPreferencesKey("age")
        private val CITY_KEY = stringPreferencesKey("city")
        private val WEIGHT_KEY = intPreferencesKey("weight")
        private val HEIGHT_KEY = intPreferencesKey("height")
        private val BMI_KEY = intPreferencesKey("bmi")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}