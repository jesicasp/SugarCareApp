package com.pa.sugarcare.models.request

import com.google.gson.annotations.SerializedName

 class ConsumeProductRequest(

	@field:SerializedName("percentage_consumed")
	val percentageConsumed: Int,

	@field:SerializedName("product_id")
	val productId: Int
)
