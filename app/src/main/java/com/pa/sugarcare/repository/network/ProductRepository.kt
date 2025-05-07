package com.pa.sugarcare.repository.network

import com.pa.sugarcare.datasource.network.ApiConfig.Companion.apiService
import com.pa.sugarcare.models.response.SearchProductResponse
import retrofit2.Response

class ProductRepository {

    suspend fun searchProductByName(q: String = "a"): Response<SearchProductResponse> {
        return apiService.searchProduct(q)
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