package com.example.conocimientosbasicosv0

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.widget.ImageButton

class CuidadoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuidadores)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            // Finaliza la actividad actual
            finish()
        }

        // Configuración inicial del RecyclerView con un adaptador vacío.
        val recyclerView = findViewById<RecyclerView>(R.id.cuidadoresRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CuidadoresAdapter(emptyList())

        val servicioId = intent.getIntExtra("EXTRA_SERVICIO_ID", -1)
        if (servicioId != -1) {
            cargarCuidadores(servicioId)

        } else {
            // Error handling: Servicio ID no encontrado.
        }
    }



    private fun cargarCuidadores(servicioId: Int) {
        RetrofitClient.create().getServiciosCuidador(servicioId).enqueue(object : Callback<Map<String, List<String>>> {
            override fun onResponse(call: Call<Map<String, List<String>>>, response: Response<Map<String, List<String>>>) {
                if (response.isSuccessful) {
                    val resultadoMap = response.body() ?: emptyMap()
                    val cuidadoresList = resultadoMap.map { (nombreCompleto, detalles) ->
                        Cuidador(
                            nombre = detalles.getOrNull(0) ?: "",
                            apellido1 = detalles.getOrNull(1) ?: "",
                            apellido2 = detalles.getOrNull(2) ?: "",
                            id = detalles.getOrNull(3)?.toIntOrNull() ?: 0
                        )
                    }

                    val recyclerView = findViewById<RecyclerView>(R.id.cuidadoresRecyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@CuidadoresActivity)
                    recyclerView.adapter = CuidadoresAdapter(cuidadoresList)
                } else {
                    // Manejar respuesta no exitosa
                    Log.d("CuidadoresActivity", "Error al obtener cuidadores, código de respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
                // Manejar fallo en la llamada
                Log.e("CuidadoresActivity", "Fallo al obtener los cuidadores", t)
            }
        })
    }


}

