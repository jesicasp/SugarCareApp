package com.pa.sugarcare.datasource.network

import com.pa.sugarcare.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {

//        private val authInterceptor = Interceptor { chain ->
//            val req = chain.request()
//            val requestHeaders = req.newBuilder()
//                .addHeader("Authorization", BuildConfig.KEY)
//                .build()
//            chain.proceed(requestHeaders)
//        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Singleton ApiService
        val apiService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}
