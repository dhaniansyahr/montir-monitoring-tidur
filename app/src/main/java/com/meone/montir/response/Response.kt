package com.meone.montir.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String
)

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("userId")
	val userId: String? = null
)
