package com.meone.montir.viewModel.sleep

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.meone.montir.data.pref.AlarmModel
import com.meone.montir.data.pref.UserModel
import com.meone.montir.data.repository.AlarmRepository
import com.meone.montir.data.repository.UserRepository
import kotlinx.coroutines.launch

class SleepTrackerViewModel(val repository: UserRepository, val alarmRepository: AlarmRepository) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    init {
        viewModelScope.launch {
            repository.getToken().asLiveData().observeForever {
                Log.d("OBSERVE TOKEN", it)
                _token.value = it
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getAlarmTime(): LiveData<String> {
        return alarmRepository.getAlarm().asLiveData()
    }

    fun getSleepTime(): LiveData<String> {
        return alarmRepository.getSleepTime().asLiveData()
    }

    fun setSleepTracking(alarm: AlarmModel) {
        viewModelScope.launch {
            alarmRepository.saveAlarm(alarm)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}