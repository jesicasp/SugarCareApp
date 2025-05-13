package com.pa.sugarcare.presentation.feature.report.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.YearlyListResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class YearlyRepVm(private val userRepository: UserRepository) : ViewModel() {
    private val _yearlyList =
        MutableLiveData<Resources<CommonResponse<List<YearlyListResponse>>>>()
    val yearlyList: LiveData<Resources<CommonResponse<List<YearlyListResponse>>>> =
        _yearlyList


    fun getYearlyList() {
        viewModelScope.launch {
            _yearlyList.postValue(Resources.Loading)
            try {
                val response = userRepository.getYearlyList()
                Log.e(TAG, "Response code: ${response.code()}")
                val dataList = response.body()?.data

                dataList?.forEachIndexed { index, item ->
                    Log.e(TAG, "Item $index: ${item.year} ")
                }
                if (response.isSuccessful && response.body() != null) {
                    _yearlyList.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _yearlyList.postValue(Resources.Error("Get failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _yearlyList.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }


    companion object {
        private const val TAG = "YearlyRepVM"
    }
}