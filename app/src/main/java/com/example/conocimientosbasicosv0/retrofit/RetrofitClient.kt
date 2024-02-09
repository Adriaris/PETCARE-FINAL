package com.example.conocimientosbasicosv0.retrofit

import com.example.proyectom13.retrofit.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "http://10.118.2.249:8080"


        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(APIService::class.java)
        }
    }
}

