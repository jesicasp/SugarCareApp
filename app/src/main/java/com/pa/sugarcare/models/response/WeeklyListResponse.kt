package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

class WeeklyListResponse(

    @field:SerializedName("month")
    val month: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("report")
    val report: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("week_number")
    val weekNumber: Int? = null
)
