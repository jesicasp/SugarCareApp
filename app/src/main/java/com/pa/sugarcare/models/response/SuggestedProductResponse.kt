package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

data class SuggestedProductResponse(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("servings_per_package")
	val servingsPerPackage: String? = null,

	@field:SerializedName("serving_size_ml")
	val servingSizeMl: String? = null,

	@field:SerializedName("gr_sugar_content")
	val grSugarContent: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("net_weight")
	val netWeight: String? = null
)