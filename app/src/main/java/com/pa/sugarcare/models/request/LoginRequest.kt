package com.pa.sugarcare.models.request

import com.google.gson.annotations.SerializedName

 class LoginRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
