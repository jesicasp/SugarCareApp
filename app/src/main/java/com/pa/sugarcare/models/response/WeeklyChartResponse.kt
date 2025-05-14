package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName
import com.pa.sugarcare.R

class WeeklyChartResponse(

    @field:SerializedName("month")
    val month: String,

    @field:SerializedName("year")
    val year: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("sugar_grade")
    val sugarGrade: String,

    @field:SerializedName("day")
    val day: Int,

    @field:SerializedName("total_sugar")
    val totalSugar: Int,

    var colorResId: Int = R.color.teal_green

)
