package com.example.conocimientosbasicosv0.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.adapter.FavoritosAdapter
import com.example.conocimientosbasicosv0.api.RetrofitClient
import com.example.conocimientosbasicosv0.model.Cuidador
import com.example.conocimientosbasicosv0.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritosAdapter: FavoritosAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)

        val sessionManager = SessionManager(requireContext())
        val idCuenta = sessionManager.getLoggedInAccount()?.idCuenta

        idCuenta?.let { cargarCuidadoresFavoritos(it) }
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        favoritosAdapter = FavoritosAdapter(emptyList())
        recyclerView.adapter = favoritosAdapter
    }

    private fun cargarCuidadoresFavoritos(idCuenta: Int) {
        RetrofitClient.create().getCuidadoresFavoritos(idCuenta).enqueue(object : Callback<List<Cuidador>> {
            override fun onResponse(call: Call<List<Cuidador>>, response: Response<List<Cuidador>>) {
                if (response.isSuccessful) {
                    val cuidadoresFavoritos = response.body() ?: emptyList()
                    favoritosAdapter = FavoritosAdapter(cuidadoresFavoritos)
                    recyclerView.adapter = favoritosAdapter
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            override fun onFailure(call: Call<List<Cuidador>>, t: Throwable) {
                // Manejar fallo en la llamada
            }
        })
    }
}
