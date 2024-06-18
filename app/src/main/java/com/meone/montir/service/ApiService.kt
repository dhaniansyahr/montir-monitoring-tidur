package com.meone.montir.service

import com.meone.montir.response.LoginResponse
import com.meone.montir.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    fun register (
        @Body requestBody: Register
    ): Call<RegisterResponse>

    @POST("login")
    fun login (
        @Body requestBody: Login
    ): Call<LoginResponse>
}