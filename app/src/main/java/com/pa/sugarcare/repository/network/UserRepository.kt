package com.pa.sugarcare.repository.network

import com.pa.sugarcare.datasource.network.ApiConfig
import com.pa.sugarcare.datasource.response.LoginResponse
import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.utility.TokenStorage
import retrofit2.Response

class UserRepository() {
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        val response = ApiConfig.apiService.login(request)

        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!.data.token
            TokenStorage.saveToken(token)
        }
        return response
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository().also { instance = it }
            }
        }
    }
}