package com.example.conocimientosbasicosv0.api

import com.example.proyectom13.retrofit.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        //private const val BASE_URL = "http://192.168.5.241:8080"
        private const val BASE_URL = "http://10.118.2.98:8080"
        //private const val BASE_URL = "http://192.168.1.36:8080"
        //private const val BASE_URL = "http://192.168.1.58:8080"
        fun create(): APIService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(APIService::class.java)
        }
    }
}

