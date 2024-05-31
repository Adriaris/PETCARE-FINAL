package com.example.conocimientosbasicosv0.data

import java.io.Serializable

data class MascotaInfo(
    val idMascota: Int?,
    val nombre: String,
    val raza: String?,
    val animal: String?,
    val edad: Int?,
    val peso: Float?,
    val enfermedades: String?,
    val descripcion: String?
) : Serializable


