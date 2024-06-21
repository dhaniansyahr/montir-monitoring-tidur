package com.meone.montir.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class GetSleepDurResponse(
    @SerializedName("data") val data: List<GetSleepDur>
)
data class GetSleepDur(
    @SerializedName("body")
    val id : Int,
    val user_id : String,
    val age : Int,
    val city : String,
    val gender : Int,
    val bmi : Float,
    val stress_level : Int,
    val sleep_duration : Int,
    val date : String,
    val quality_score : Float
)
