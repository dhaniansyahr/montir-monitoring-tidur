package com.meone.montir.data.repository

import com.meone.montir.data.pref.AlarmModel
import com.meone.montir.data.pref.AlarmPreference
import kotlinx.coroutines.flow.Flow

class AlarmRepository private constructor(
    private val alarmPreference: AlarmPreference,
) {

    suspend fun saveAlarm(alarm: AlarmModel) {
        alarmPreference.saveAlarm(alarm)
    }

    fun getAlarm(): Flow<String> {
        return alarmPreference.getAlarm()
    }

    fun getSleepTime(): Flow<String> {
        return alarmPreference.getSleepTime()
    }

    companion object {
        @Volatile
        private var instance: AlarmRepository? = null
        fun getInstance(
            alarmPreference: AlarmPreference,
        ): AlarmRepository =
            instance ?: synchronized(this) {
                instance ?: AlarmRepository(alarmPreference)
            }.also { instance = it }
    }
}