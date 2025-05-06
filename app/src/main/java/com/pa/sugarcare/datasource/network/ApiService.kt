package com.pa.sugarcare.datasource.network

import com.pa.sugarcare.datasource.response.LoginResponse
import com.pa.sugarcare.models.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //login
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}