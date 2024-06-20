package com.meone.montir.service

import com.meone.montir.response.GetSleepDur
import com.meone.montir.response.SleepDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SleepDataApi {
    @GET("dailydata")
    fun getSleepDuration(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<List<GetSleepDur>>
}

