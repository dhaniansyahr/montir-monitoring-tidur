package com.meone.montir.service

data class Register(
    val username: String,
    val password: String,
    val age: Int,
    val city: String,
    val gender: Boolean,
    val height: Int,
    val weight: Int
)

data class Login(
    val username: String,
    val password: String
)

data class RequestDailyData(
    val stress_level: Int,
    val sleep_duration: Float
)
