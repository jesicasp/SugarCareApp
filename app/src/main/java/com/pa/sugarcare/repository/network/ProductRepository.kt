package com.pa.sugarcare.repository.network

import com.pa.sugarcare.datasource.network.ApiConfig.Companion.apiService
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.ConsumedProductResponse
import com.pa.sugarcare.models.response.DetailProductResponse
import com.pa.sugarcare.models.response.ProductDataSearchHistory
import com.pa.sugarcare.models.response.SearchProductResponse
import retrofit2.Response

class ProductRepository {
    suspend fun getAllProduct(): Response<SearchProductResponse> {
        return apiService.getAllProduct()
    }

    suspend fun getProductById(id: Int): Response<CommonResponse<DetailProductResponse>> {
        return apiService.getProductById(id)
    }

    suspend fun searchProductByName(q: String = "a"): Response<SearchProductResponse> {
        return apiService.searchProduct(q)
    }

    suspend fun postSearchProduct(request: SearchProductRequest): Response<CommonResponse<Nothing>> {
        return apiService.postSearchProduct(request)
    }

    suspend fun getSearchProduct(): Response<CommonResponse<List<ProductDataSearchHistory>>> {
        return apiService.getUserSearchedProduct()
    }

    suspend fun getConsumedProduct(): Response<CommonResponse<List<ConsumedProductResponse>>> {
        return apiService.getUserConsumedProduct()
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