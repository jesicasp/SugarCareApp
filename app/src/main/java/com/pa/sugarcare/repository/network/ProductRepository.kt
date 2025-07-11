package com.pa.sugarcare.repository.network

import com.pa.sugarcare.datasource.network.ApiConfig.Companion.apiService
import com.pa.sugarcare.models.request.ConsumeProductRequest
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.models.request.SuggestedProductRequest
//import com.pa.sugarcare.models.request.SuggestedProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.ConsumedProductResponse
import com.pa.sugarcare.models.response.DetailProductResponse
import com.pa.sugarcare.models.response.ProductDataSearchHistory
import com.pa.sugarcare.models.response.RecProductResponse
import com.pa.sugarcare.models.response.SearchProductResponse
import com.pa.sugarcare.models.response.SuggestedProductResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class ProductRepository {
    suspend fun getAllProduct(): Response<SearchProductResponse> {
        return apiService.getAllProduct()
    }

    suspend fun getProductById(id: Int): Response<CommonResponse<DetailProductResponse>> {
        return apiService.getProductById(id)
    }

    suspend fun getRecProduct(id: Int): Response<CommonResponse<List<RecProductResponse>>> {
        return apiService.getReProduct(id)
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

    suspend fun postConsumeProduct(request: ConsumeProductRequest): Response<CommonResponse<Nothing>> {
        return apiService.postUserConsumption(request)
    }

    suspend fun postSuggestProduct(
        imageFile: File,
        request: SuggestedProductRequest
    ): Response<CommonResponse<SuggestedProductResponse>> {

        val imagePart = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )

        val dataMap = request.toRequestBodyMap()

        return apiService.postSuggestProduct(imagePart, dataMap)
    }

    fun SuggestedProductRequest.toRequestBodyMap(): Map<String, @JvmSuppressWildcards RequestBody> {
        return mapOf(
            "name" to name.toRequestBody("text/plain".toMediaTypeOrNull()),
            "category" to category.toRequestBody("text/plain".toMediaTypeOrNull()),
            "gr_sugar_content" to grSugarContent.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull()),
            "net_weight" to netWeight.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            "servings_per_package" to servingsPerPackage.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull()),
            "serving_size_ml" to servingSizeMl.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
        )
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