package com.example.proyectom13.retrofit


import com.example.conocimientosbasicosv0.Cuenta
import com.example.conocimientosbasicosv0.Cuidador
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.Call
import retrofit2.http.PUT
import retrofit2.http.Path


interface APIService {

    @POST("registroCuenta")
    fun registrarCuenta(@Body cuenta: Cuenta): Call<Boolean>

    @POST("loginCuenta")
    fun loginCuenta(@Body cuenta: Cuenta): Call<Cuenta>

}


