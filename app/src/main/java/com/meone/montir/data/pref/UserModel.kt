package com.meone.montir.data.pref

data class UserModel(
    val userId: String,
    val name: String,
    val isLogin: Boolean = false
)