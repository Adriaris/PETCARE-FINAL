package com.example.conocimientosbasicosv0.activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.conocimientosbasicosv0.R
class CuidadorProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuidador_profile)

        val username = intent.getStringExtra("username")
        val nombre = intent.getStringExtra("nombre")
        val apellido1 = intent.getStringExtra("apellido1")
        val apellido2 = intent.getStringExtra("apellido2")
        val descripcionCorta = intent.getStringExtra("descripcionCorta")
        val descripcionLarga = intent.getStringExtra("descripcionLarga")
        val mascotas = intent.getStringArrayListExtra("mascotas")

        findViewById<TextView>(R.id.usernameTextView).text = username
        findViewById<TextView>(R.id.nombreApellidoTextView).text = "$nombre $apellido1 $apellido2"
        findViewById<TextView>(R.id.descripcionCortaTextView).text = descripcionCorta
        findViewById<TextView>(R.id.descripcionLargaTextView).text = descripcionLarga

        // Aquí puedes agregar la lógica para mostrar las mascotas si es necesario.

        findViewById<Button>(R.id.solicitarReservaButton).setOnClickListener {
            // Aquí puedes agregar la lógica para solicitar una reserva.
        }
    }
}