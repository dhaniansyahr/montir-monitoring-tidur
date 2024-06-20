package com.meone.montir.viewModel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.meone.montir.data.repository.UserRepository
import com.meone.montir.response.ResponseGetUser
import com.meone.montir.response.ResponseUpdateUser
import com.meone.montir.service.ApiConfig
import com.meone.montir.service.RequestUpdateUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _value = MutableLiveData<ResponseGetUser>()
    val value: LiveData<ResponseGetUser> = _value

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _response = MutableLiveData<ResponseUpdateUser>()
    val response: LiveData<ResponseUpdateUser> = _response

    private val _isLoadingUpdate = MutableLiveData<Boolean>()
    val isLoadingUpdate: LiveData<Boolean> = _isLoadingUpdate

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name



    init {
        getUser()

        viewModelScope.launch {
            repository.getSession().asLiveData().observeForever {
                if (it != null) {
                    _name.value = it.name
                }
            }
        }
    }

    fun getUser() {
        _isLoading.value = true

        viewModelScope.launch {
            repository.getSession().asLiveData().observeForever {
                if (it != null) {
                    val client = ApiConfig.getApiService().getUser("Bearer ${it.token}", it.userId)

                    client.enqueue(object : Callback<ResponseGetUser> {
                        override fun onResponse(
                            call: Call<ResponseGetUser>,
                            response: Response<ResponseGetUser>
                        ) {
                            _isLoading.value = false
                            if (response.isSuccessful) {
                                _value.value = response.body()
                            } else {
                                Log.e("Create Story", "onFailure ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<ResponseGetUser>, t: Throwable) {
                            _isLoading.value = false
                            Log.e("Create Story", "onFailure ${t.message.toString()}")
                        }
                    })
                }

            }
        }
    }

    fun updateUser(reqBody: RequestUpdateUser){
        _isLoadingUpdate.value = true
        viewModelScope.launch {
            repository.getSession().asLiveData().observeForever {
                if (it != null) {
                    val client = ApiConfig.getApiService().updateUser("Bearer ${it.token}", it.userId, reqBody)

                    client.enqueue(object : Callback<ResponseUpdateUser> {
                        override fun onResponse(
                            call: Call<ResponseUpdateUser>,
                            response: Response<ResponseUpdateUser>
                        ) {
                            _isLoadingUpdate.value = false
                            if (response.isSuccessful) {
                                _response.value = response.body()
                            } else {
                                Log.e("Create Story", "onFailure ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<ResponseUpdateUser>, t: Throwable) {
                            _isLoadingUpdate.value = false
                            Log.e("Create Story", "onFailure ${t.message.toString()}")
                        }
                    })
                }

            }
        }
    }
}