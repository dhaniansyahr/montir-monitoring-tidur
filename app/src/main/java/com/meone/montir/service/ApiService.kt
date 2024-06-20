package com.meone.montir.service

import com.meone.montir.response.GetSleepDur
import com.meone.montir.response.GetSleepDurResponse
import com.meone.montir.response.LoginResponse
import com.meone.montir.response.RegisterResponse
import com.meone.montir.response.ResponseDailyData
import com.meone.montir.response.ResponseMusic
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("users")
    fun register (
        @Body requestBody: Register
    ): Call<RegisterResponse>

    @POST("login")
    fun login (
        @Body requestBody: Login
    ): Call<LoginResponse>

    @GET("audios")
    fun getAudios(): Call<ResponseMusic>

    @GET("/dailydata")
    fun getSleepDuration(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<GetSleepDurResponse>

    @POST("users/daily")
    fun createDaily(
        @Header("Authorization") token: String,
        @Body requestBody: RequestDailyData
    ): Call<ResponseDailyData>
}