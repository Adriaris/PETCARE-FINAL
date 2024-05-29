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

class ReservasFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservasAdapter: ReservasAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)

        // Obtener el ID de la cuenta desde el utils.SessionManager
        val sessionManager = SessionManager(requireContext())
        val idCuenta = sessionManager.getLoggedInAccount()?.idCuenta

        // Si el ID de la cuenta no es nulo, cargar las reservas
        idCuenta?.let { cargarReservas(it) }
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewReservas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        reservasAdapter = ReservasAdapter(emptyList())
        recyclerView.adapter = reservasAdapter
    }

    private fun cargarReservas(idCuenta: Int) {
        RetrofitClient.create().getReservas(idCuenta).enqueue(object : Callback<Map<String, List<String>>> {
            override fun onResponse(call: Call<Map<String, List<String>>>, response: Response<Map<String, List<String>>>) {
                if (response.isSuccessful) {
                    // Transformar la respuesta en una lista de objetos Reserva
                    val reservasList = response.body()?.map { Reserva(it.key, it.value) } ?: emptyList()
                    reservasAdapter.updateReservas(reservasList)
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
                // Manejar fallo en la llamada
            }
        })
    }}


