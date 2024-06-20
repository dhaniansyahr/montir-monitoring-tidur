package com.meone.montir.viewModel.music

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meone.montir.response.DataItemMusic
import com.meone.montir.response.ResponseMusic
import com.meone.montir.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel: ViewModel() {

    private val _musicList = MutableLiveData<List<DataItemMusic>>()
    val musicList: LiveData<List<DataItemMusic>> = _musicList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MusicViewModel"
    }

    init {
        getMusic()
    }


    private fun getMusic() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAudios()

        client.enqueue(object : Callback<ResponseMusic> {
            override fun onResponse(call: Call<ResponseMusic>, response: Response<ResponseMusic>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _musicList.value = response.body()?.data
                } else {
//                    handleErrorResponse(response)
                    Log.e(TAG, "onFailure Enqueue: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseMusic>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}