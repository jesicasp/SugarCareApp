package com.pa.sugarcare.datasource.network

import com.pa.sugarcare.models.request.ConsumeProductRequest
import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.models.request.RegisterRequest
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.models.request.UpdateUserRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.ConsumedProductResponse
import com.pa.sugarcare.models.response.DataUserToken
import com.pa.sugarcare.models.response.DetailProductResponse
import com.pa.sugarcare.models.response.ProductDataSearchHistory
import com.pa.sugarcare.models.response.SearchProductResponse
import com.pa.sugarcare.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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

    //get all product
    @GET("api/products")
    suspend fun getAllProduct(): Response<SearchProductResponse>

    @GET("api/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<CommonResponse<DetailProductResponse>>

    //search product by name
    @GET("api/products/search")
    suspend fun searchProduct(
        @Query("q") query: String
    ): Response<SearchProductResponse>

    //save user search history
    @POST("api/user-search-history-product")
    suspend fun postSearchProduct(
        @Body request: SearchProductRequest
    ): Response<CommonResponse<Nothing>>

    //post consume product
    @POST("api/user-consumption")
    suspend fun postUserConsumption(
        @Body request: ConsumeProductRequest
    ): Response<CommonResponse<Nothing>>

    //get detail
    @GET("api/user")
    suspend fun getDetailUser(
    ): Response<UserResponse>

    //edit user data
    @PATCH("api/user")
    suspend fun updateUser(
        @Body request: UpdateUserRequest
    ): Response<UserResponse>

    //get list product that searched by user
    @GET("api/user-search-history-product")
    suspend fun getUserSearchedProduct(): Response<CommonResponse<List<ProductDataSearchHistory>>>

    //get list product that consumed by user
    @GET("api/user-consumption")
    suspend fun getUserConsumedProduct(): Response<CommonResponse<List<ConsumedProductResponse>>>
}