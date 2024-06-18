package com.meone.montir.view.sleep

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.meone.montir.data.pref.UserModel
import com.meone.montir.data.repository.UserRepository
import kotlinx.coroutines.launch

class SleepTrackerViewModel(val repository: UserRepository) : ViewModel() {

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

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}