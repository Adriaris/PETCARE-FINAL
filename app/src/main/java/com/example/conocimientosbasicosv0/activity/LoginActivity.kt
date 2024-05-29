package com.example.conocimientosbasicosv0.activity

import com.example.conocimientosbasicosv0.utils.SessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.api.RetrofitClient
import com.example.conocimientosbasicosv0.model.Cuenta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    var apiService = RetrofitClient.create()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.UserEmailAddress)
        passwordEditText = findViewById(R.id.UserTextPassword)
        loginButton = findViewById(R.id.button2)
        registerTextView = findViewById(R.id.textViewRegister)


        loginButton.setOnClickListener {
            Log.d("LOGIN", "HE PULSADO EL BOTON DE LOGIN")
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val datosUser = arrayListOf(email, password)
                iniciarSesion(datosUser)
            } else {
                Toast.makeText(this, "Por favor, ingresa tus credenciales", Toast.LENGTH_SHORT).show()
            }
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion(datosUser: ArrayList<String>) {
        apiService.loginCuenta(datosUser).enqueue(object : Callback<Cuenta> {
            override fun onResponse(call: Call<Cuenta>, response: Response<Cuenta>) {
                if (response.isSuccessful && response.body() != null) {
                    val cuenta = response.body()!!
                    val sessionManager = SessionManager(this@LoginActivity)
                    sessionManager.saveLoggedInAccount(cuenta)
                    Log.d("TIPO CUENTA", "Tipo de cuenta: ${cuenta.tipoPerfil}")
                    when (cuenta.tipoPerfil) {
                        0.toByte() -> verificarAccesoDueño(cuenta)
                        1.toByte() -> verificarAccesoCuidador(cuenta)
                        else -> Toast.makeText(this@LoginActivity, "Tipo de perfil desconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    handleLoginError(response.code())
                }
            }

            override fun onFailure(call: Call<Cuenta>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error al iniciar sesión: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun verificarAccesoDueño(cuenta: Cuenta) {
        apiService.getTimeAccessDueño(cuenta.idCuenta!!).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {

                if (response.isSuccessful) {

                    val accessTime = response.body()!!
                    if (accessTime == 0) {
                        val intent = Intent(this@LoginActivity, PopupDuenoActivity::class.java).apply {
                            putExtra("EXTRA_CUENTA", cuenta)
                        }

                        Log.d("PopupDuenoActivity", "intento abrir el popup 2")
                        startActivity(intent)
                    } else {
                        navegarHomeActivity(cuenta)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Error al verificar acceso del dueño", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error en la llamada al endpoint de acceso del dueño: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun verificarAccesoCuidador(cuenta: Cuenta) {
        apiService.getAccesTimes(cuenta.idCuenta!!).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val accessTime = response.body()!!
                    if (accessTime == 0) {
                        val intent = Intent(this@LoginActivity, PopupCuidadorActivity::class.java).apply {
                            putExtra("EXTRA_CUENTA", cuenta)
                        }
                        startActivity(intent)
                    } else {
                        navegarHomeActivity(cuenta)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Error al verificar acceso del cuidador", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error en la llamada al endpoint de acceso del cuidador: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navegarHomeActivity(cuenta: Cuenta) {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
            putExtra("EXTRA_CUENTA", cuenta)
        }
        startActivity(intent)
        finish()
    }

    private fun handleLoginError(code: Int) {
        when (code) {
            401 -> Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this, "Error desconocido: $code", Toast.LENGTH_SHORT).show()
        }
    }
}