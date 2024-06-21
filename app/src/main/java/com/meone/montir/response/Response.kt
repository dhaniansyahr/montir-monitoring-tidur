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

data class ResponseMusic(

	@field:SerializedName("data")
	val data: List<DataItemMusic>,

	@field:SerializedName("message")
	val message: String
)

data class DataItemMusic(

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("urlMusic")
	val urlMusic: String,

	@field:SerializedName("musicName")
	val musicName: String
)

data class ResponseDailyData(

	@field:SerializedName("data")
	val data: DataDaily,

	@field:SerializedName("message")
	val message: String
)

data class DataDaily(

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("quality_score")
	val qualityScore: Any,

	@field:SerializedName("sleep_duration")
	val sleepDuration: Int,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("stress_level")
	val stressLevel: Int,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("bmi")
	val bmi: Any
)

data class ResponseGetUser(

	@field:SerializedName("data")
	val data: DataUser,

	@field:SerializedName("message")
	val message: String
)

data class DataUser(

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("bmi_obese")
	val bmiObese: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("bmi_overweight")
	val bmiOverweight: Int,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("bmi_underweight")
	val bmiUnderweight: Int,

	@field:SerializedName("bmi_normal")
	val bmiNormal: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("bmi")
	val bmi: Float
)

data class ResponseUpdateUser(

	@field:SerializedName("data")
	val data: List<DataUpdateUser>,

	@field:SerializedName("message")
	val message: String
)

data class DataUpdateUser(

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
	val bmi: Float
)

data class ResponseUpdatePassword(

	@field:SerializedName("data")
	val data: List<DataUpdatePassword>,

	@field:SerializedName("message")
	val message: String
)

data class DataUpdatePassword(

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
