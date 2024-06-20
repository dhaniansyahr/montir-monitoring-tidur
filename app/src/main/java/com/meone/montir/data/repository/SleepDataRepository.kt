package com.meone.montir.data.repository

import android.util.Log
import com.meone.montir.response.GetSleepDur
import com.meone.montir.response.SleepDataResponse
import com.meone.montir.service.SleepDataApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SleepDataRepository {
//    private val api: SleepDataApi
    private val TAG:String = "CHECK_RESPONSE_STATISTICACTIVITY"

//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://montir-cvzxsttw2a-et.a.run.app/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        api = retrofit.create(SleepDataApi::class.java)
//    }
//    fun fetchSleepData(startDate: String, endDate: String, callback: (List<GetSleepDur>) -> Unit) {
//        api.getSleepDuration(startDate, endDate).enqueue(object : Callback<List<GetSleepDur>> {
////            override fun onResponse(call: Call<List<GetSleepDur>>, response: Response<List<GetSleepDur>>) {
////                if (response.isSuccessful) {
////                    response.body()?.let {
////                        for (sleepdur in it){
////                            Log.i(TAG, "onResponse: ${sleepdur.sleep_duration}")
////                        }
////                    }
////                }
////            }
//
//            override fun onFailure(call: Call<List<GetSleepDur>>, t: Throwable) {
//                Log.i(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }
}