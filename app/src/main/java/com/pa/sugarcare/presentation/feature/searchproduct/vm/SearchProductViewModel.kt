package com.pa.sugarcare.presentation.feature.searchproduct.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.SearchProductResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class SearchProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _productsResult = MutableLiveData<Resources<SearchProductResponse>>()
    val productsResult: LiveData<Resources<SearchProductResponse>> = _productsResult

    private val defaultProduct = "a"

    init {
        findProducts(defaultProduct)
    }

    fun findProducts(q: String) {
        viewModelScope.launch {
            _productsResult.postValue(Resources.Loading)
            try {
                val response = productRepository.searchProductByName(q)
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")
                // Log setiap nama produk dalam list
                response.body()?.data?.forEach { item ->
                    Log.e(TAG, "Product name: ${item.name}, Sugar content: ${item.grSugarContent}")
                }


                if (response.isSuccessful && response.body() != null) {
                    _productsResult.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _productsResult.postValue(Resources.Error("Login failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _productsResult.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }

    }

    companion object {
        private const val TAG = "SearchProductVM"
    }
}