package com.pa.sugarcare.models.response

 class SearchProductNameResponse(
	val data: List<ProductDataItem>
)

 class ProductDataItem(
	val image: String,
	val sugarGrade: String,
	val name: String,
	val id: Int,
	val grSugarContent: Int,
	val netWeight: Int
)

