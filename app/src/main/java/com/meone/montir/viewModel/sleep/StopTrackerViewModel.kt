package com.meone.montir.viewModel.sleep

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.meone.montir.data.repository.UserRepository
import com.meone.montir.response.ResponseDailyData
import com.meone.montir.service.ApiConfig
import com.meone.montir.service.RequestDailyData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StopTrackerViewModel(private val repository: UserRepository) : ViewModel() {

    private val _response = MutableLiveData<ResponseDailyData>()
    val response: LiveData<ResponseDailyData> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun createDailyData(stressLevel: Int, sleepDuration: Float) {
        _isLoading.value = true
        val reqBody = RequestDailyData(stressLevel, sleepDuration)

        Log.d("REQUEST BODY: ", stressLevel.toString())
        Log.d("REQUEST BODY: ", sleepDuration.toString())

        viewModelScope.launch {
            repository.getToken().asLiveData().observeForever {
                if (it.isNotEmpty()) {
                    val client = ApiConfig.getApiService().createDaily("Bearer ${it}", reqBody)

                    client.enqueue(object : Callback<ResponseDailyData> {
                        override fun onResponse(
                            call: Call<ResponseDailyData>,
                            response: Response<ResponseDailyData>
                        ) {
                            _isLoading.value = false
                            if (response.isSuccessful) {
                                _response.value = response.body()
                            } else {
                                Log.e("Create Story", "onFailure ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<ResponseDailyData>, t: Throwable) {
                            _isLoading.value = false
                            Log.e("Create Story", "onFailure ${t.message.toString()}")
                        }
                    })
                }

            }
        }
    }

}