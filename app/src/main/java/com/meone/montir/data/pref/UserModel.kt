package com.meone.montir.data.pref

data class UserModel(
    val userId: String,
    val name: String,
    val token: String,
    val gender: Int,
    val city: String,
    val weight: Int,
    val height: Int,
    val age: Int,
    val isLogin: Boolean = false,
)