package com.example.conocimientosbasicosv0

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conocimientosbasicosv0.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun openRegisterActivity(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun openHomeActivityByLogin(view: View) {
        val userEmailInput: EditText = findViewById(R.id.UserEmailAddress)
        val userPasswdInput: EditText = findViewById(R.id.UserTextPassword)
        val userEmail = userEmailInput.text.toString()
        val userPasswd = userPasswdInput.text.toString()

        if (userEmail.isNotEmpty() && userPasswd.isNotEmpty()) {
            val datosUser = arrayListOf(userEmail, userPasswd)
            val apiService = RetrofitClient.create()

            apiService.loginCuenta(datosUser).enqueue(object : Callback<Cuenta> {
                override fun onResponse(call: Call<Cuenta>, response: Response<Cuenta>) {
                    if (response.isSuccessful && response.body() != null) {
                        // Guardar la cuenta en el almacenamiento local
                        val sessionManager = SessionManager(this@LoginActivity)
                        sessionManager.saveLoggedInAccount(response.body()!!)
                        // Abrir la actividad Home
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish() // Finalizar esta actividad
                    } else {
                        handleLoginError(response.code())
                    }
                }

                override fun onFailure(call: Call<Cuenta>, t: Throwable) {
                    handleNetworkError(t)
                }
            })
        } else {
            Toast.makeText(this, "Por favor, rellena ambos campos", Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleLoginError(errorCode: Int) {
        // Manejar diferentes tipos de errores de login
        when (errorCode) {
            401 -> Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()

            else -> Toast.makeText(this@LoginActivity, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleNetworkError(throwable: Throwable) {
        // Manejar errores de red
        Toast.makeText(this@LoginActivity, "Error de conexión: ${throwable.message}", Toast.LENGTH_SHORT).show()
    }

}