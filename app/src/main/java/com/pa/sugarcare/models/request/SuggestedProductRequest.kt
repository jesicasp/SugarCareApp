package com.pa.sugarcare.models.request

data class SuggestedProductRequest(
    val name: String,
    val category: String,
    val grSugarContent: Double,
    val netWeight: Double,
    val servingsPerPackage: Double,
    val servingSizeMl: Double
)