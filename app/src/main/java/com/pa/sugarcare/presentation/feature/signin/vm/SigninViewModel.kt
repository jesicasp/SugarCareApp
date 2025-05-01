package com.pa.sugarcare.presentation.feature.signin.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.datasource.response.LoginResponse
import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class SigninViewModel(private val userRepository: UserRepository) : ViewModel(){
    private val _loginResult = MutableLiveData<Resources<LoginResponse>>()
    val loginResult: LiveData<Resources<LoginResponse>> = _loginResult

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResult.postValue(Resources.Loading)

            try {
                val response = userRepository.login(request)
                if (response.isSuccessful && response.body() != null) {
                    _loginResult.postValue(Resources.Success(response.body()!!))
                } else {
                    _loginResult.postValue(Resources.Error("Login failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                _loginResult.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }
        }
    }
}