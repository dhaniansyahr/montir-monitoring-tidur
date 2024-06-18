package com.meone.montir.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.alarmDataStore: DataStore<Preferences> by preferencesDataStore(name = "alarm_pref")

class AlarmPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveAlarm(alarm: AlarmModel) {
        dataStore.edit { preferences ->
            preferences[ALARM_TIME] = alarm.alarmTime
            preferences[SLEEP_TIME] = alarm.sleepTime
        }
    }

    fun getAlarm(): Flow<String> {
        return dataStore.data.map {
            it[ALARM_TIME] ?: ""
        }
    }

    fun getSleepTime(): Flow<String> {
        return dataStore.data.map {
            it[SLEEP_TIME] ?: ""
        }
    }

    suspend fun clearAlarm() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AlarmPreference? = null

        private val ALARM_TIME = stringPreferencesKey("alarmTime")
        private val SLEEP_TIME = stringPreferencesKey("sleepTime")

        fun getInstance(dataStore: DataStore<Preferences>): AlarmPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = AlarmPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}