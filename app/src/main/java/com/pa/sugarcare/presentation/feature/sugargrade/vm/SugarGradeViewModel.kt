package com.pa.sugarcare.presentation.feature.sugargrade.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.ConsumeProductRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DetailProductResponse
import com.pa.sugarcare.models.response.RecProductResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class SugarGradeViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _detailProduct = MutableLiveData<Resources<CommonResponse<DetailProductResponse>>>()
    val detailProduct: LiveData<Resources<CommonResponse<DetailProductResponse>>> = _detailProduct

    private val _consumeProduct = MutableLiveData<Resources<CommonResponse<Nothing>>>()
    val consumeProduct: LiveData<Resources<CommonResponse<Nothing>>> = _consumeProduct

    private val _listRecProduct =
        MutableLiveData<Resources<CommonResponse<List<RecProductResponse>>>>()
    val listRecProduct: LiveData<Resources<CommonResponse<List<RecProductResponse>>>> =
        _listRecProduct

    fun getDetailProduct(id: Int) {
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

    fun postConsumeProduct(request: ConsumeProductRequest) {
        viewModelScope.launch {
            _consumeProduct.postValue(Resources.Loading)
            try {
                val response = productRepository.postConsumeProduct(request)
                Log.e(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _consumeProduct.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _consumeProduct.postValue(Resources.Error("Post Product failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _consumeProduct.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    fun getRecProduct(id: Int) {
        viewModelScope.launch {
            _listRecProduct.postValue(Resources.Loading)
            try {
                val response = productRepository.getRecProduct(id)
                Log.d(TAG, "Product ID: {$id}")
                Log.e(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _listRecProduct.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _listRecProduct.postValue(Resources.Error("Get Rec Product failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _listRecProduct.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    companion object {
        private const val TAG = "SugarGradeVM"
    }
}