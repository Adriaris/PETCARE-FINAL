package com.example.proyectom13.retrofit


import com.example.conocimientosbasicosv0.data.MascotaInfo
import com.example.conocimientosbasicosv0.model.Cuenta
import com.example.conocimientosbasicosv0.model.Mascota
import com.example.conocimientosbasicosv0.model.Raza
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.PUT
import retrofit2.http.Path


interface APIService {

    @POST("registroCuenta")
    fun registrarCuenta(@Body datosUser: ArrayList<String>): Call<Boolean>

    @POST("loginCuenta")
    fun loginCuenta(@Body datosUser: ArrayList<String>): Call<Cuenta>

    @GET("getServicios")
    fun getServicios(): Call<Map<String, String>>

    @GET("getServiciosCuidador/{id}") //id del srevicio que ofrece el cuidador
    fun getServiciosCuidador(@Path("id") id: Int): Call<Map<String, List<String>>>

    //@GET("getReservas/{idCuenta}")
    //fun getReservas(@Path("idCuenta") idCuenta: Int): Call<Map<String, List<String>>>

    @PUT("updateC")
    fun updateC(@Body cuentaMap: Map<String, String>): Call<Void>

    @GET("getTimeAccessDueño/{idDueno}")
    fun getTimeAccessDueño(@Path("idDueno") idDueno: Int): Call<Int>

    @PUT("addDueñoAccess/{idDueno}")
    fun addDueñoAccess(@Path("idDueno") idDueno: Int): Call<Void>

    @GET("getAccesTimes/{idCuidador}")
    fun getAccesTimes(@Path("idCuidador") idCuidador: Int): Call<Int>

    @PUT("addCuidadorAccess/{idCuidador}")
    fun addCuidadorAccess(@Path("idCuidador") idCuidador: Int): Call<Void>

    @GET("getAnimales")
    fun getAnimales(): Call<List<String>>

    @GET("getRazas/{idAnimal}")
    fun getRazas(@Path("idAnimal") idAnimal: Int): Call<Map<String, Raza>>


    @POST("addMascota")
    fun addMascota(
        @Body mascotaInfo: List<String>
    ): Call<Void>

    @POST("addDueño")
    fun addDueño(@Body emails: List<String>): Call<Void>

    @GET("getMascotasDueño/{idDueno}")
    fun getMascotasByDueño(@Path("idDueno") idDueno: Int): Call<Map<String, MascotaInfo>>

    @POST("addReserva")
    fun addReserva(@Body datosReserva: Map<String, Int>): Call<Void>

    @POST("setMascotaReservada")
    fun setMascotaReservada(@Body idsReservaMascotas: List<Int>): Call<Void>

    @GET("getMascotasReservadas/{idDueno}")
    fun getMascotasReservadas(@Path("idDueno") idDueño: Int): Call<Map<String, String>>

    @GET("getReservasDueño/{idDueno}")
    fun getReservas(@Path("idDueno") idDueno: Int): Call<Map<String, Map<String, String>>>






}


