package com.example.conocimientosbasicosv0.model

data class ReservaMascotaRequest(
    val idReserva: Int,
    val listaMascotas: List<Int>
)