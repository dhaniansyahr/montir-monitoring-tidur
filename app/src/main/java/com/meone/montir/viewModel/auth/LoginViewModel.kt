package com.meone.montir.viewModel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meone.montir.data.pref.UserModel
import com.meone.montir.data.repository.UserRepository
import com.meone.montir.response.LoginResponse
import com.meone.montir.service.ApiConfig
import com.meone.montir.service.Login
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _value = MutableLiveData<LoginResponse>()
    val value: LiveData<LoginResponse> = _value

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val post = Login(email, password)
        val client = ApiConfig.getApiService().login(post)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _value.value = response.body()
                    _message.value = response.body()?.message!!

                } else {
                    Log.e("Login User", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Login User", "onFailure: ${t.message.toString()}")
                _message.value = t.message.toString()
            }
        })
    }
}