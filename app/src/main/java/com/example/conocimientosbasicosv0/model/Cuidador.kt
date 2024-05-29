package com.example.conocimientosbasicosv0.model

data class Cuidador(
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val id: Int,
    val username: String,
    val descripcionCorta: String,
    val descripcionLarga: String,
    val mascotas: List<String>
)



