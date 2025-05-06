package com.pa.sugarcare.models.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: T?, // T adalah tipe data yang bisa bervariasi

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)


data class User(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String
)

data class DataUserToken(

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("token")
    val token: String
)