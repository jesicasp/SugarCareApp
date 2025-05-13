package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

 class WeeklyListResponse(

	@field:SerializedName("month")
	val month: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("year")
	val year: Int,

	@field:SerializedName("report")
	val report: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("week_number")
	val weekNumber: Int
)
