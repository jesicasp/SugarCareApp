package com.pa.sugarcare.presentation.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.SearchProductNameResponse
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _productsResult = MutableLiveData<Resources<CommonResponse<SearchProductNameResponse>>>()
    val productsResult: LiveData<Resources<CommonResponse<SearchProductNameResponse>>> = _productsResult


    fun findProducts(q: String) {
        viewModelScope.launch {
            _productsResult.postValue(Resources.Loading)

            try {
                val response = productRepository.searchProductByName(q)
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")

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
        private const val TAG = "HomeViewModel"
    }
}