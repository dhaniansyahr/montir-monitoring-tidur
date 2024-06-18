package com.meone.montir.di

import android.content.Context
import com.meone.montir.data.pref.AlarmPreference
import com.meone.montir.data.pref.UserPreference
import com.meone.montir.data.pref.alarmDataStore
import com.meone.montir.data.pref.dataStore
import com.meone.montir.data.repository.AlarmRepository
import com.meone.montir.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
    fun provideAlarmRepository(context: Context): AlarmRepository {
        val pref = AlarmPreference.getInstance(context.alarmDataStore)
        return AlarmRepository.getInstance(pref)
    }
}