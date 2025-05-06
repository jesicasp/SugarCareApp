package com.pa.sugarcare.presentation.feature.signup.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.RegisterRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DataUserToken
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Resources<CommonResponse<DataUserToken>>>()
    val registerResult: LiveData<Resources<CommonResponse<DataUserToken>>> = _registerResult

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _registerResult.postValue(Resources.Loading)

            try {
                val response = userRepository.register(request)
                Log.d(TAG, "Email: ${request.email}, Password: ${request.password}, CPass: ${request.passwordConfirmation}, Name: ${request.name}")
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.data?.token
                    Log.e(TAG, "Token Register: $token")

                    _registerResult.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error body: $errorMessage")
                    _registerResult.postValue(Resources.Error("Login failed: $errorMessage"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _registerResult.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }


        }

    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }
}