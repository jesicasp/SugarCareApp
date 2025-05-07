package com.pa.sugarcare.datasource.network

import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.models.request.RegisterRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DataUserToken
import com.pa.sugarcare.models.response.SearchProductNameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    //search product by name
    @GET("api/products/search")
    suspend fun searchProduct(
        @Query("q") query: String
    ): Response<CommonResponse<SearchProductNameResponse>>


}