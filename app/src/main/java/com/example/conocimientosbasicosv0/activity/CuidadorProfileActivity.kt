package com.example.conocimientosbasicosv0.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.api.RetrofitClient
import com.example.conocimientosbasicosv0.data.MascotaInfo
import com.example.conocimientosbasicosv0.model.ReservaMascotaRequest
import com.example.conocimientosbasicosv0.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuidadorProfileActivity : AppCompatActivity() {

    private var cuidadorId: Int = 0
    private lateinit var sessionManager: SessionManager
    private lateinit var mascotasContainer: LinearLayout
    private lateinit var solicitarReservaButton: Button
    private lateinit var addToFavoritesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuidador_profile)

        sessionManager = SessionManager(this)
        val dueñoId = sessionManager.getIdCuenta()

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener { finish() }

        val username = intent.getStringExtra("username")
        val nombre = intent.getStringExtra("nombre")
        val apellido1 = intent.getStringExtra("apellido1")
        val apellido2 = intent.getStringExtra("apellido2")
        val descripcionCorta = intent.getStringExtra("descripcionCorta")
        val descripcionLarga = intent.getStringExtra("descripcionLarga")
        cuidadorId = intent.getIntExtra("id", 0)

        findViewById<TextView>(R.id.usernameTextView).text = username
        findViewById<TextView>(R.id.nombreApellidoTextView).text = "$nombre $apellido1 $apellido2"
        findViewById<TextView>(R.id.descripcionCortaTextView).text = descripcionCorta
        findViewById<TextView>(R.id.descripcionLargaTextView).text = descripcionLarga

        mascotasContainer = findViewById(R.id.mascotasContainer)
        solicitarReservaButton = findViewById(R.id.solicitarReservaButton)
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton)
        solicitarReservaButton.isEnabled = false

        cargarMascotas(dueñoId)

        solicitarReservaButton.setOnClickListener {
            val selectedMascotas = obtenerMascotasSeleccionadas()
            if (selectedMascotas.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos una mascota", Toast.LENGTH_SHORT).show()
            } else {
                solicitarReserva(cuidadorId, dueñoId, selectedMascotas)
            }
        }

        addToFavoritesButton.setOnClickListener {
            añadirACuidadorFavorito(dueñoId, cuidadorId)
        }
    }

    private fun cargarMascotas(dueñoId: Int) {
        RetrofitClient.create().getMascotasByDueño(dueñoId).enqueue(object : Callback<Map<String, MascotaInfo>> {
            override fun onResponse(call: Call<Map<String, MascotaInfo>>, response: Response<Map<String, MascotaInfo>>) {
                if (response.isSuccessful) {
                    val mascotas = response.body()
                    mascotas?.forEach { (_, mascotaInfo) ->
                        val checkBox = CheckBox(this@CuidadorProfileActivity)
                        checkBox.text = mascotaInfo.nombre
                        checkBox.tag = mascotaInfo.idMascota // Aseguramos que el tag sea Int
                        checkBox.setOnCheckedChangeListener { _, _ -> validarSeleccion() }
                        mascotasContainer.addView(checkBox)
                    }
                } else {
                    Toast.makeText(this@CuidadorProfileActivity, "Error al cargar mascotas", Toast.LENGTH_SHORT).show()
                    Log.e("CuidadorProfileActivity", "Error al cargar mascotas: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Map<String, MascotaInfo>>, t: Throwable) {
                Toast.makeText(this@CuidadorProfileActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("CuidadorProfileActivity", "Fallo en la conexión", t)
            }
        })
    }

    private fun validarSeleccion() {
        solicitarReservaButton.isEnabled = obtenerMascotasSeleccionadas().isNotEmpty()
    }

    private fun obtenerMascotasSeleccionadas(): List<Int> {
        val selectedMascotas = mutableListOf<Int>()
        for (i in 0 until mascotasContainer.childCount) {
            val checkBox = mascotasContainer.getChildAt(i) as CheckBox
            if (checkBox.isChecked) {
                selectedMascotas.add(checkBox.tag as Int)
            }
        }
        return selectedMascotas
    }

    private fun solicitarReserva(idCuidador: Int, idDueño: Int, selectedMascotas: List<Int>) {
        val datosReserva = mapOf("idCuidador" to idCuidador, "idDueño" to idDueño)
        val call = RetrofitClient.create().addReserva(datosReserva)

        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val idReserva = response.body() ?: return
                    setMascotaReservada(idReserva, selectedMascotas)
                } else {
                    Toast.makeText(this@CuidadorProfileActivity, "Error al realizar la reserva", Toast.LENGTH_SHORT).show()
                    Log.e("CuidadorProfileActivity", "Error al realizar la reserva: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@CuidadorProfileActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("CuidadorProfileActivity", "Fallo en la conexión", t)
            }
        })
    }

    private fun setMascotaReservada(idReserva: Int, selectedMascotas: List<Int>) {
        val idsReservaMascotas = ReservaMascotaRequest(idReserva, selectedMascotas)
        Log.d("setMascotaReservada", "Enviando datos al endpoint: $idsReservaMascotas")
        val call = RetrofitClient.create().setMascotaReservada(idsReservaMascotas)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CuidadorProfileActivity, "Reserva y asignación de mascotas realizadas con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CuidadorProfileActivity, "Error al asignar las mascotas a la reserva", Toast.LENGTH_SHORT).show()
                    Log.e("setMascotaReservada", "Error al asignar las mascotas a la reserva: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CuidadorProfileActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("setMascotaReservada", "Fallo en la conexión", t)
            }
        })
    }

    private fun añadirACuidadorFavorito(idDueño: Int, idCuidador: Int) {
        val idsCuidadores = mapOf("idCuidador" to idCuidador, "idDueño" to idDueño)
        Log.d("añadirACuidadorFavorito", "Enviando datos al endpoint: $idsCuidadores")
        val call = RetrofitClient.create().setCuidadorFavorito(idsCuidadores)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CuidadorProfileActivity, "Cuidador añadido a favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CuidadorProfileActivity, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
                    Log.e("añadirACuidadorFavorito", "Error al añadir a favoritos: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CuidadorProfileActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("añadirACuidadorFavorito", "Fallo en la conexión", t)
            }
        })
    }
}
