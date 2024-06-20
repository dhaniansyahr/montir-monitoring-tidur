package com.meone.montir.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meone.montir.data.repository.AlarmRepository
import com.meone.montir.data.repository.UserRepository
import com.meone.montir.di.Injection
import com.meone.montir.viewModel.auth.LoginViewModel
import com.meone.montir.viewModel.auth.RegisterViewModel
import com.meone.montir.viewModel.music.MusicViewModel
import com.meone.montir.viewModel.sleep.SleepTrackerViewModel

class ViewModelFactory(private val repository: UserRepository, private val alarmRepository: AlarmRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StatisticViewModel::class.java) -> {
                StatisticViewModel() as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel() as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SleepTrackerViewModel::class.java) -> {
                SleepTrackerViewModel(repository, alarmRepository) as T
            }
            modelClass.isAssignableFrom(MusicViewModel::class.java) -> {
                MusicViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), Injection.provideAlarmRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}