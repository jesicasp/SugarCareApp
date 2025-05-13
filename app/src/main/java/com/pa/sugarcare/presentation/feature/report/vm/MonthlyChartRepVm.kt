package com.pa.sugarcare.presentation.feature.report.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.R
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.MonthlyChartResponse
import com.pa.sugarcare.models.response.WeeklyListResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class MonthlyChartRepVm(private val userRepository: UserRepository) : ViewModel() {
    private val _monthlyData =
        MutableLiveData<Resources<CommonResponse<List<MonthlyChartResponse>>>>()
    val monthlyData: LiveData<Resources<CommonResponse<List<MonthlyChartResponse>>>> =
        _monthlyData



    fun getMonthlyData(month: String, year: Int) {
        viewModelScope.launch {
            _monthlyData.postValue(Resources.Loading)
            try {
                val response = userRepository.getMonthlyConsumption(month, year)
                Log.e(TAG, "Response code: ${response.code()}")
                val dataList = response.body()?.data

                dataList?.forEachIndexed { index, item ->
                    Log.e(TAG, "Item $index: ${item.weekNumber} ${item.totalSugar} ")
                }
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val mappedData = data.data?.map { item ->
                        item.colorResId = getColorForSugarGrade(item.sugarGrade)
                        item
                    }

                    val updatedResponse = data.copy(data = mappedData)
                    _monthlyData.postValue(Resources.Success(updatedResponse))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _monthlyData.postValue(Resources.Error("Get failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _monthlyData.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    fun getColorForSugarGrade(grade: String): Int {
        return when (grade.lowercase()) {
            "merah" -> R.color.red
            "kuning" -> R.color.yellow
            "hijau" -> R.color.green
            else -> R.color.teal_green
        }
    }


    companion object {
        private const val TAG = "MonthlyChartVM"
    }
}