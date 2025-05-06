package com.pa.sugarcare.models.request

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,
)
