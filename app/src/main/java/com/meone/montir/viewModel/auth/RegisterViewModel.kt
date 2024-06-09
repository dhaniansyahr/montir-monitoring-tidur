package com.meone.montir.viewModel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meone.montir.response.RegisterResponse
import com.meone.montir.service.ApiConfig
import com.meone.montir.service.Register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _response = MutableLiveData<RegisterResponse>()
    val response: LiveData<RegisterResponse> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    fun register(username: String, email: String, password: String, age: Int, city: String, gender: Boolean, height: Int, weight: Int) {
        _isLoading.value = true
        val post = Register(username, email, password, age, city, gender, height, weight)
        val client = ApiConfig.getApiService().register(post)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _response.value = response.body()
                    _message.value = response.body()?.message!!

                } else {
                    Log.e("Register User", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Register User", "onFailure: ${t.message.toString()}")
                _message.value = t.message
            }
        })
    }
}