package com.pa.sugarcare.datasource.response

import com.google.gson.annotations.SerializedName

 class LoginResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

 class User(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)

 class Data(

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
)
