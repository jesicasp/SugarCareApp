package com.pa.sugarcare.presentation.feature.report.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.MonthlyListResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class MonthlyRepVm(private val userRepository: UserRepository) : ViewModel() {
    private val _monthlyList =
        MutableLiveData<Resources<CommonResponse<List<MonthlyListResponse>>>>()
    val monthlyList: LiveData<Resources<CommonResponse<List<MonthlyListResponse>>>> =
        _monthlyList


    fun getMonthlyList() {
        viewModelScope.launch {
            _monthlyList.postValue(Resources.Loading)
            try {
                val response = userRepository.getMonthlyList()
                Log.e(TAG, "Response code: ${response.code()}")
                val dataList = response.body()?.data

                dataList?.forEachIndexed { index, item ->
                    Log.e(TAG, "Item $index: ${item.month} ${item.year} ")
                }
                if (response.isSuccessful && response.body() != null) {
                    _monthlyList.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _monthlyList.postValue(Resources.Error("Get failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _monthlyList.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }


    companion object {
        private const val TAG = "MonthlyRepVM"
    }
}