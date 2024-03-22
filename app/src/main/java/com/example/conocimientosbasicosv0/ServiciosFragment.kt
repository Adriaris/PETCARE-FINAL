package com.example.conocimientosbasicosv0

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.conocimientosbasicosv0.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class ServiciosFragment : Fragment() {
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_servicios, container, false)
        cargarServicios(view)
        return view
    }

    private fun cargarServicios(view: View) {
        RetrofitClient.create().getServicios().enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                if (response.isSuccessful) {
                    val servicios = response.body()
                    servicios?.let { mostrarServicios(view, it) }
                } else {
                    // Manejar error en un futuro...
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Manejar error en un futuro...
            }
        })
    }

    private fun mostrarServicios(view: View, servicios: List<String>) {
        val layout = view.findViewById<LinearLayout>(R.id.layoutServicios)
        layout.removeAllViews() // Limpiar antes de agregar nuevos botones
        servicios.forEachIndexed { index, servicio ->
            val botonServicio = Button(context).apply {
                text = servicio
                // Los servicios comienzan en 1 y son consecutivos
                val servicioId = index + 1
                setOnClickListener {
                    abrirCuidadoresActivity(servicioId)
                }
            }
            layout.addView(botonServicio)
        }
    }

    private fun abrirCuidadoresActivity(servicioId: Int) {
        val intent = Intent(requireContext(), CuidadoresActivity::class.java).apply {
            putExtra("EXTRA_SERVICIO_ID", servicioId)
        }
        startActivity(intent)
    }


    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): ServiciosFragment {
            return ServiciosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }
}
