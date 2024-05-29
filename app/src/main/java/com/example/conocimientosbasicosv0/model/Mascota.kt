package com.example.conocimientosbasicosv0.model

data class Mascota(
    val descEnfermedades: String,
    val descSobreMascota: String,
    val edad: Int,
    val nombre: String,
    val peso: Float,
    val alimento: Int?,
    val duenyo: Int?,
    val raza: Int?
)

