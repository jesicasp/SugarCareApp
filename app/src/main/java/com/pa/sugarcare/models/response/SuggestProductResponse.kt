package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

data class SuggestProductResponse(

	@field:SerializedName("data")
	val data: Data? = null
)