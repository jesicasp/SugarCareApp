package com.pa.sugarcare.presentation.feature.sugargrade.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.SearchProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DetailProductResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class SugarGradeViewModel (private val productRepository: ProductRepository) : ViewModel() {
    private val _detailProduct = MutableLiveData<Resources<CommonResponse<DetailProductResponse>>>()
    val detailProduct: LiveData<Resources<CommonResponse<DetailProductResponse>>> = _detailProduct

    fun getDetailProduct(id : Int) {
        viewModelScope.launch {
            _detailProduct.postValue(Resources.Loading)
            try {
                val response = productRepository.getProductById(id)
                Log.d(TAG, "Product ID: {$id}")
                Log.e(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _detailProduct.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _detailProduct.postValue(Resources.Error("Get Detail Product failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _detailProduct.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    companion object {
        private const val TAG = "SugarGradeVM"
    }
}