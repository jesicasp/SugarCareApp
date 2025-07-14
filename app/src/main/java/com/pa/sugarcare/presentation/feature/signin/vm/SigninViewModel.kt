package com.pa.sugarcare.presentation.feature.signin.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DataUserToken
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch
import org.json.JSONObject

class SigninViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Resources<CommonResponse<DataUserToken>>>()
    val loginResult: LiveData<Resources<CommonResponse<DataUserToken>>> = _loginResult

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResult.postValue(Resources.Loading)

            try {
                val response = userRepository.login(request)
                Log.d(TAG, "Email: ${request.email}, Password: ${request.password}")
                Log.e(TAG, "Response code: ${response.code()}")
                Log.e(TAG, "Response headers: ${response.headers()}")

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.data?.token
                    _loginResult.postValue(Resources.Success(response.body()!!))
                } else {
                    val errorBody = response.errorBody()?.string()
                    var message = "Terjadi kesalahan"

                    errorBody?.let {
                        try {
                            val jsonObject = JSONObject(it)
                            message = jsonObject.optString("message", message)
                        } catch (e: Exception) {
                            Log.e(TAG, "Gagal parsing errorBody: ${e.message}")
                        }
                    }

                    Log.e(TAG, "Error message: $message")
                    _loginResult.postValue(Resources.Error(message))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _loginResult.postValue(Resources.Error(e.message ?: "Terjadi kesalahan tak terduga"))
            }
        }
    }


    companion object {
        private const val TAG = "SignInViewModel"
    }

}