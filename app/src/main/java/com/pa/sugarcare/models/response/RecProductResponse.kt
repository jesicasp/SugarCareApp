package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

 class RecProductResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("sugar_grade")
	val sugarGrade: String,

	@field:SerializedName("product_id")
	val productId: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("gr_sugar_content")
	val grSugarContent: Int,

	@field:SerializedName("net_weight")
	val netWeight: String
)
