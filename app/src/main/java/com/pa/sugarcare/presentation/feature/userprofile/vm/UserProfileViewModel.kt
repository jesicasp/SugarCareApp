package com.pa.sugarcare.presentation.feature.userprofile.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _logoutResult = MutableLiveData<Resources<CommonResponse<Nothing>>>()
    val logoutResult: LiveData<Resources<CommonResponse<Nothing>>> = _logoutResult

    fun logout() {
        Log.d("LOGOUTT","viewmodel logout()")

        viewModelScope.launch {
            _logoutResult.postValue(Resources.Loading)

            try {
                val response = userRepository.logout()
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    _logoutResult.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _logoutResult.postValue(Resources.Error("Logout failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _logoutResult.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }


        }

    }

    companion object {
        private const val TAG = "UserProfileVm"
    }
}