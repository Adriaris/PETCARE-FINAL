package com.example.conocimientosbasicosv0.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.api.RetrofitClient
import com.example.conocimientosbasicosv0.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuidadorProfileActivity : AppCompatActivity() {

    private var cuidadorId: Int = 0
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuidador_profile)

        // Inicializar SessionManager
        sessionManager = SessionManager(this)
        val dueñoId = sessionManager.getIdCuenta()

        // Botón de volver atrás
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val username = intent.getStringExtra("username")
        val nombre = intent.getStringExtra("nombre")
        val apellido1 = intent.getStringExtra("apellido1")
        val apellido2 = intent.getStringExtra("apellido2")

        val descripcionLarga = intent.getStringExtra("descripcionLarga")
        cuidadorId = intent.getIntExtra("id", 0)

        findViewById<TextView>(R.id.usernameTextView).text = username
        findViewById<TextView>(R.id.nombreApellidoTextView).text = "$nombre $apellido1 $apellido2"

        findViewById<TextView>(R.id.descripcionLargaTextView).text = descripcionLarga

        findViewById<Button>(R.id.solicitarReservaButton).setOnClickListener {
            solicitarReserva(cuidadorId, dueñoId)
        }
    }

    private fun solicitarReserva(idCuidador: Int, idDueño: Int) {
        val datosReserva = mapOf("idCuidador" to idCuidador, "idDueño" to idDueño)
        val call = RetrofitClient.create().addReserva(datosReserva)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CuidadorProfileActivity, "Reserva realizada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CuidadorProfileActivity, "Error al realizar la reserva", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CuidadorProfileActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
