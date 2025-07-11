package com.pa.sugarcare.presentation.feature.suggestproduct.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.SuggestedProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.SuggestedProductResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch
import java.io.File

class SuggestProductVm(private val productRepository: ProductRepository) : ViewModel() {
    private val _sProduct = MutableLiveData<Resources<CommonResponse<SuggestedProductResponse>>>()
    val sProduct: LiveData<Resources<CommonResponse<SuggestedProductResponse>>> = _sProduct

    fun postConsumeProduct(imageFile: File, request: SuggestedProductRequest) {
        viewModelScope.launch {
            _sProduct.postValue(Resources.Loading)
            try {
                val response = productRepository.postSuggestProduct(imageFile, request)
                Log.e(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _sProduct.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _sProduct.postValue(Resources.Error("Post Product failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _sProduct.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    companion object {
        private const val TAG = "SuggestProductVm"
    }
}