package com.pa.sugarcare.presentation.feature.userprofile.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.ConsumedProductResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class MyConsumptionViewModel (private val productRepository: ProductRepository) : ViewModel() {

    private val _productsResult = MutableLiveData<Resources<CommonResponse<List<ConsumedProductResponse>>>>()
    val productsResult: LiveData<Resources<CommonResponse<List<ConsumedProductResponse>>>> = _productsResult

    fun getProducts() {
        viewModelScope.launch {
            _productsResult.postValue(Resources.Loading)
            try {
                val response = productRepository.getConsumedProduct()
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")
                response.body()?.data?.forEach { item ->
                    Log.e(TAG, "Product name: ${item.productName}")
                }

                if (response.isSuccessful && response.body() != null) {
                    _productsResult.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _productsResult.postValue(Resources.Error("Get Product failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _productsResult.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    companion object {
        private const val TAG = "MyConsumptionVM"
    }
}