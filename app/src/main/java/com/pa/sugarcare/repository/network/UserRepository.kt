package com.pa.sugarcare.repository.network

import android.util.Log
import com.pa.sugarcare.datasource.network.ApiConfig.Companion.apiService
import com.pa.sugarcare.models.request.LoginRequest
import com.pa.sugarcare.models.request.RegisterRequest
import com.pa.sugarcare.models.request.UpdateUserRequest
import com.pa.sugarcare.models.response.CommonResponse
import com.pa.sugarcare.models.response.DataUserToken
import com.pa.sugarcare.models.response.MonthlyListResponse
import com.pa.sugarcare.models.response.UserResponse
import com.pa.sugarcare.models.response.WeeklyListResponse
import com.pa.sugarcare.models.response.YearlyListResponse
import com.pa.sugarcare.utility.TokenStorage
import retrofit2.Response

class UserRepository {
    suspend fun login(request: LoginRequest): Response<CommonResponse<DataUserToken>> {
        val response = apiService.login(request)

        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!.data?.token!!
            TokenStorage.saveToken(token)
        }
        return response
    }

    suspend fun register(request: RegisterRequest): Response<CommonResponse<DataUserToken>> {
        val response = apiService.register(request)

        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!.data?.token!!
            TokenStorage.saveToken(token)
        }
        return response
    }

    suspend fun logout(): Response<CommonResponse<Nothing>> {
        Log.d("LOGOUTT", "userrepo logout()")
        val response = apiService.logout()

        if (response.isSuccessful && response.body() != null) {
            TokenStorage.clearToken()
        }
        return response
    }

    suspend fun getDetail(): Response<UserResponse> {
        return apiService.getDetailUser()
    }

    suspend fun updateUser(request: UpdateUserRequest): Response<UserResponse> {
        return apiService.updateUser(request)
    }

    suspend fun getTodaySugarConsumed(): Response<CommonResponse<Double?>> {
        return apiService.getTodaySugarConsumed()
    }

    suspend fun getWeeklyList(): Response<CommonResponse<List<WeeklyListResponse>>> {
        return apiService.getWeeklyList()
    }

    suspend fun getMonthlyList(): Response<CommonResponse<List<MonthlyListResponse>>> {
        return apiService.getMonthlyList()
    }

    suspend fun getYearlyList(): Response<CommonResponse<List<YearlyListResponse>>> {
        return apiService.getYearlyList()
    }

    suspend fun searchReport(query: String): Response<CommonResponse<List<WeeklyListResponse>>> {
        return apiService.searchReport(query)
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