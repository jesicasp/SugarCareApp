package com.pa.sugarcare.presentation.feature.userprofile.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.UserResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class EditProfileViewModel (private val userRepository: UserRepository) : ViewModel(){

    private val _dataUser = MutableLiveData<Resources<UserResponse>>()
    val dataUser: LiveData<Resources<UserResponse>> = _dataUser

    fun getDetailUser() {
        Log.d("DETAIL USER","viewmodel getDetailUser()")

        viewModelScope.launch {
            _dataUser.postValue(Resources.Loading)
            try {
                val response = userRepository.getDetail()
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    _dataUser.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _dataUser.postValue(Resources.Error("Get detail user failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _dataUser.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }


        }

    }

    companion object {
        private const val TAG = "EditProfileVm"
    }
}