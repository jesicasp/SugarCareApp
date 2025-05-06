package com.pa.sugarcare.datasource.network

import android.util.Log
import com.pa.sugarcare.utility.TokenStorage
import okhttp3.Interceptor
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = TokenStorage.getToken()
        val requestBuilder = chain.request().newBuilder()

        val isLoginRequest = chain.request().url.toString().contains("login", true)
        val isRegisterRequest = chain.request().url.toString().contains("register", true)

        if (!(isLoginRequest || isRegisterRequest) && token != null) {
            Log.d("AuthInterceptor", "Menambahkan token ke header: Bearer $token")
            requestBuilder.addHeader("Authorization", "Bearer $token")
        } else {
            Log.d("AuthInterceptor", "Request login/register, token tidak ditambahkan.")
        }

        val request = requestBuilder.build()

        Log.d("AuthInterceptor", "Request URL: ${request.url}")
        Log.d("AuthInterceptor", "Request Headers: ${request.headers}")

        return chain.proceed(request)
    }
}

