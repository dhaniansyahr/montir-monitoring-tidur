package com.meone.montir.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class GetSleepDur(
    @SerializedName("body")
    val id : Int,
    val user_id : String,
    val age : Int,
    val city : String,
    val gender : Boolean,
    val bmi : Float,
    val stress_level : Int,
    val sleep_duration : Int,
    val date : Date,
    val quality_score : Float
)
