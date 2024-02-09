package com.example.conocimientosbasicosv0

import android.content.Intent
import android.os.Bundle
import retrofit2.Call

import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conocimientosbasicosv0.retrofit.RetrofitClient
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val confirmPasswordEditText: EditText = findViewById(R.id.editTextConfirmPassword)
        val registerButton: Button = findViewById(R.id.buttonRegister)

        registerButton.setOnClickListener {

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (validateRegistrationData(email, password, confirmPassword)) {
                registerUser(email, password)
            } else {
                // Los mensajes de error se manejan dentro de validateRegistrationData
            }
        }
    }

    fun openLoginActivity(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun registerUser(email: String, password: String) {
        val cuenta = Cuenta(
            email = email,
            passwd = password
        )

        val apiService = RetrofitClient.create()
        apiService.registrarCuenta(cuenta).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() == true) {
                    // Registro exitoso, redirigir al LoginActivity
                    Toast.makeText(this@RegisterActivity, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Manejar el error de registro
                    Toast.makeText(this@RegisterActivity, "Registro fallido: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                // Manejar fallo en la llamada
                Toast.makeText(this@RegisterActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun validateRegistrationData(email: String, password: String, confirmPassword: String): Boolean {
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Por favor, ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "La contraseña no cumple con los requisitos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        //val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        //return password.matches(passwordPattern.toRegex())
        return true;
    }
}

