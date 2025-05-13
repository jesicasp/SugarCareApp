package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName
import com.pa.sugarcare.R

class YearlyChartResponse(

	@field:SerializedName("month")
	val month: String,

	@field:SerializedName("sugar_grade")
	val sugarGrade: String,

	@field:SerializedName("total_sugar")
	val totalSugar: Double,

	@field:SerializedName("total_days")
	val totalDays: Int,

	var colorResId: Int = R.color.teal_green

 )
