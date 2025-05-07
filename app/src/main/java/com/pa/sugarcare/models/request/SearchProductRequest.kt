package com.pa.sugarcare.models.request

import com.google.gson.annotations.SerializedName

 class SearchProductRequest(

	@field:SerializedName("product_id")
	val productId: Int
)
