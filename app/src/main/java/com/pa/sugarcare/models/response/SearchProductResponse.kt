package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

 class SearchProductResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

 class DataItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("sugar_grade")
	val sugarGrade: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gr_sugar_content")
	val grSugarContent: Double,

	@field:SerializedName("net_weight")
	val netWeight: Double,

	@field:SerializedName("serving_size_ml")
	val servSize: Double,


)
