package com.pa.sugarcare.presentation.feature.sugargrade.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class ProductResultViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableLiveData<Resources<CommonResponse<Nothing>>>()
    val product: LiveData<Resources<CommonResponse<Nothing>>> = _product

     fun postProduct(request : SearchProductRequest) {
        viewModelScope.launch {
            _product.postValue(Resources.Loading)
            try {
                val response = productRepository.postSearchProduct(request)
                Log.d(TAG, "Product ID: ${request.productId}")
                Log.e(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _product.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _product.postValue(Resources.Error("Post failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _product.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    companion object{
        private const val TAG = "ProductResultVM"
    }
}