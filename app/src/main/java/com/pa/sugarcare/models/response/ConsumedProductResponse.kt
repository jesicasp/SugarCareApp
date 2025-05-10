package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

 class ConsumedProductResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("amountConsumed")
	val amountConsumed: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("sugar_grade")
	val sugarGrade: String,

	@field:SerializedName("gr_sugar_consumed")
	val grSugarConsumed: Double,

	@field:SerializedName("product_image")
	val productImage: String,

	@field:SerializedName("product_id")
	val productId: Int,

	@field:SerializedName("product_name")
	val productName: String
)
