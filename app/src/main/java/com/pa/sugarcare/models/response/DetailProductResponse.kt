package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

 class DetailProductResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("sugar_grade")
	val sugarGrade: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("servings_per_package")
	val servingsPerPackage: String,

	@field:SerializedName("varians")
	val varians: List<VariansItem>,

	@field:SerializedName("information")
	val information: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gr_sugar_content")
	val grSugarContent: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("net_weight")
	val netWeight: Int
)

 class VariansItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
