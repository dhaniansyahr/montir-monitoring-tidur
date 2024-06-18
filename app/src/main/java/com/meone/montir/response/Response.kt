package com.meone.montir.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String
)

data class DataItem(

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("bmi")
	val bmi: Any
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
