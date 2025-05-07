package com.pa.sugarcare.repository.network

import com.pa.sugarcare.datasource.network.ApiConfig.Companion.apiService
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.SearchProductResponse
import retrofit2.Response

class ProductRepository {
    suspend fun getAllProduct(): Response<SearchProductResponse> {
        return apiService.getAllProduct()
    }

    suspend fun searchProductByName(q: String = "a"): Response<SearchProductResponse> {
        return apiService.searchProduct(q)
    }

    suspend fun postSearchProduct(request : SearchProductRequest): Response<CommonResponse<Nothing>> {
        return apiService.postSearchProduct(request)
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository {
            return instance ?: synchronized(this) {
                instance ?: ProductRepository().also { instance = it }
            }
        }
    }
}