package com.example.conocimientosbasicosv0.fragment

import com.example.conocimientosbasicosv0.utils.SessionManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.adapter.ReservasAdapter
import com.example.conocimientosbasicosv0.api.RetrofitClient
import com.example.conocimientosbasicosv0.model.Reserva
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservasCuidadorFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservasAdapter: ReservasAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)

        val sessionManager = SessionManager(requireContext())
        val idCuidador = sessionManager.getIdCuenta()

        if (idCuidador != 0) {
            cargarReservasCuidador(idCuidador)
        }
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewReservas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        reservasAdapter = ReservasAdapter(emptyList())
        recyclerView.adapter = reservasAdapter
    }

    private fun cargarReservasCuidador(idCuidador: Int) {
        RetrofitClient.create().getReservasCuidador(idCuidador).enqueue(object : Callback<Map<String, Map<String, Any>>> {
            override fun onResponse(call: Call<Map<String, Map<String, Any>>>, response: Response<Map<String, Map<String, Any>>>) {
                if (response.isSuccessful) {
                    val reservasList = response.body()?.map {
                        val mascotas = it.value["Mascotas"] as Map<String, Map<String, String>>
                        Reserva(
                            idReserva = it.key,
                            nombreCuidador = it.value["Nombre: "] as String,
                            apellidoUno = it.value["Apellido uno"] as String,
                            apellidoDos = it.value["Apellido dos"] as String,
                            mascotas = mascotas
                            //servicio = it.value["Servicio"] as String
                        )
                    } ?: emptyList()
                    reservasAdapter.updateReservas(reservasList)
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            override fun onFailure(call: Call<Map<String, Map<String, Any>>>, t: Throwable) {
                // Manejar fallo en la llamada
            }
        })
    }
}
