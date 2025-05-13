package com.pa.sugarcare.presentation.feature.report.vm

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReportViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _todaySugarCons =
        MutableLiveData<Resources<CommonResponse<Double?>>>()
    val todaySugarCons: LiveData<Resources<CommonResponse<Double?>>> =
        _todaySugarCons

    private val _todayDate = MutableLiveData<String>()
    val todayDate: LiveData<String> get() = _todayDate

    fun getTodaySugarCons() {
        viewModelScope.launch {
            _todaySugarCons.postValue(Resources.Loading)
            try {
                val response = userRepository.getTodaySugarConsumed()
                Log.d(TAG, "Product ID: ${response.body()?.data}")
                Log.e(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    _todaySugarCons.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _todaySugarCons.postValue(Resources.Error("Get failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _todaySugarCons.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getToday() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        val formattedDate = today.format(formatter)
        _todayDate.value = formattedDate
    }


    companion object {
        private const val TAG = "ReportVM"
    }
}