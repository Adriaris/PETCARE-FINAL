package com.example.conocimientosbasicosv0

import java.io.Serializable

data class Cuenta(
    val idCuenta: Int? = null,
    val email: String? = null,
    val passwd: String? = null,
    val apellidoDos: String? = null,
    val apellidoUno: String? = null,
    val nombre: String? = null,
    val numMovil: Int? = null,
    val numTelefono: Int? = null,
    val tipoPerfil: Byte? = null,
    val urlFotoPerfil: String? = null,
    val username: String? = null
) : Serializable




