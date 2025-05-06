package com.pa.sugarcare.models.request

import com.google.gson.annotations.SerializedName

 class RegisterRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("password_confirmation")
	val passwordConfirmation: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)
