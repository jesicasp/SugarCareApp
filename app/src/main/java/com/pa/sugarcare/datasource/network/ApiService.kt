package com.pa.sugarcare.datasource.network

import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.models.request.RegisterRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DataUserToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //login
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<CommonResponse<DataUserToken>>

    //register
    @POST("api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<CommonResponse<DataUserToken>>

    //logout
    @POST("api/logout")
    suspend fun logout(): Response<CommonResponse<Nothing>>


}