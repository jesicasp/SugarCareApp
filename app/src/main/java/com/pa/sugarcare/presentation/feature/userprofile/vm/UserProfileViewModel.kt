package com.pa.sugarcare.presentation.feature.userprofile.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.UpdateUserRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.UserResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _logoutResult = MutableLiveData<Resources<CommonResponse<Nothing>>>()
    val logoutResult: LiveData<Resources<CommonResponse<Nothing>>> = _logoutResult

    private val _dataUser = MutableLiveData<Resources<UserResponse>>()
    val dataUser: LiveData<Resources<UserResponse>> = _dataUser

    private val _updateUser = MutableLiveData<Resources<UserResponse>>()
    val updateUser: LiveData<Resources<UserResponse>> = _updateUser

    fun logout() {
        Log.d(TAG_LOGOUT, "viewmodel logout()")

        viewModelScope.launch {
            _logoutResult.postValue(Resources.Loading)

            try {
                val response = userRepository.logout()
                Log.e(TAG_LOGOUT, "Response code: ${response.code()}")
                Log.e(TAG_LOGOUT, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    _logoutResult.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG_LOGOUT, "Error body: $errorMessage")
                    _logoutResult.postValue(Resources.Error("Logout failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG_LOGOUT, "Exception occurred: ${e.message}")
                _logoutResult.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }


        }
    }

    fun getDetailUser() {
        Log.d("DETAIL USER", "viewmodel getDetailUser()")

        viewModelScope.launch {
            _dataUser.postValue(Resources.Loading)
            try {
                val response = userRepository.getDetail()
                Log.e(TAG_DETAIL, "Response code: ${response.code()}")
                Log.e(TAG_DETAIL, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    _dataUser.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG_DETAIL, "Error body: $errorMessage")
                    _dataUser.postValue(Resources.Error("Get detail user failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG_DETAIL, "Exception occurred: ${e.message}")
                _dataUser.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }

    }

    fun updateUser(request: UpdateUserRequest) {
        Log.d("DETAIL USER", "viewmodel updateUser()")

        viewModelScope.launch {
            _updateUser.postValue(Resources.Loading)
            try {
                val response = userRepository.updateUser(request)
                Log.e(TAG_UPDATE, "Response code: ${response.code()}")
                Log.e(TAG_UPDATE, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    _updateUser.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG_UPDATE, "Error body: $errorMessage")
                    _updateUser.postValue(Resources.Error("Update detail user failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG_UPDATE, "Exception occurred: ${e.message}")
                _updateUser.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }

    }

    companion object {
        private const val TAG_LOGOUT = "LogoutViewModel"
        private const val TAG_DETAIL = "DetailUserViewModel"
        private const val TAG_UPDATE = "UpdateUserViewModel"
    }
}