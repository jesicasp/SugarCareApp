package com.pa.sugarcare.presentation.feature.report.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.WeeklyListResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class WeeklyRepVm(private val userRepository: UserRepository) : ViewModel() {
    private val _weeklyList =
        MutableLiveData<Resources<CommonResponse<List<WeeklyListResponse>>>>()
    val weeklyList: LiveData<Resources<CommonResponse<List<WeeklyListResponse>>>> =
        _weeklyList


    fun getWeeklyList() {
        viewModelScope.launch {
            _weeklyList.postValue(Resources.Loading)
            try {
                val response = userRepository.getWeeklyList()
                Log.e(TAG, "Response code: ${response.code()}")
                val dataList = response.body()?.data

                dataList?.forEachIndexed { index, item ->
                    Log.e(TAG, "Item $index: ${item.report}")
                }
                if (response.isSuccessful && response.body() != null) {
                    _weeklyList.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _weeklyList.postValue(Resources.Error("Get failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _weeklyList.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }


    companion object {
        private const val TAG = "WeeklyRepVM"
    }
}