package com.pa.sugarcare.presentation.feature.report.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.R
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.WeeklyChartResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class WeeklyChartRepVm (private val userRepository: UserRepository) : ViewModel() {
    private val _weeklyData =
        MutableLiveData<Resources<CommonResponse<List<WeeklyChartResponse>>>>()
    val weeklyData: LiveData<Resources<CommonResponse<List<WeeklyChartResponse>>>> =
        _weeklyData



    fun getWeeklyData(sugarReportId: Int) {
        viewModelScope.launch {
            _weeklyData.postValue(Resources.Loading)
            try {
                val response = userRepository.getWeeklyConsumption(sugarReportId)
                Log.e(TAG, "Response code: ${response.code()}")
                val dataList = response.body()?.data

                dataList?.forEachIndexed { index, item ->
                    Log.e(TAG, "Item $index: ${item.day} ${item.totalSugar} ")
                }
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val mappedData = data.data?.map { item ->
                        item.colorResId = getColorForSugarGrade(item.sugarGrade)
                        item
                    }

                    val updatedResponse = data.copy(data = mappedData)
                    _weeklyData.postValue(Resources.Success(updatedResponse))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _weeklyData.postValue(Resources.Error("Get failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _weeklyData.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }

    private fun getColorForSugarGrade(grade: String): Int {
        return when (grade.lowercase()) {
            "merah" -> R.color.red
            "kuning" -> R.color.yellow
            "hijau" -> R.color.green
            else -> R.color.teal_green
        }
    }


    companion object {
        private const val TAG = "WeeklyChartVM"
    }
}