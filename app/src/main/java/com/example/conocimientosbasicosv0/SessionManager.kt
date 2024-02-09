package com.example.conocimientosbasicosv0

import android.content.Context

class SessionManager {
    fun getLoggedInAccount(context: Context): Cuenta {
        TODO("Not yet implemented")
    }

    companion object {
        private const val PREF_NAME = "MyPrefs"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "contraseña"
        private const val KEY_ID_CUENTA = "idCuenta"
        private const val KEY_APELLIDO_DOS = "apellidoDos"
        private const val KEY_APELLIDO_UNO = "apellidoUno"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_NUM_MOVIL = "numMovil"
        private const val KEY_NUM_TELEFONO = "numTelefono"
        private const val KEY_TIPO_PERFIL = "tipoPerfil"
        private const val KEY_URL_FOTO_PERFIL = "urlFotoPerfil"
        private const val KEY_USERNAME = "username"

        fun saveLoggedInAccount(context: Context, cuenta: Cuenta) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString(KEY_EMAIL, cuenta.email)
                putString(KEY_PASSWORD, cuenta.passwd)
                putInt(KEY_ID_CUENTA, cuenta.idCuenta ?: -1)
                putString(KEY_APELLIDO_DOS, cuenta.apellidoDos)
                putString(KEY_APELLIDO_UNO, cuenta.apellidoUno)
                putString(KEY_NOMBRE, cuenta.nombre)
                putInt(KEY_NUM_MOVIL, cuenta.numMovil ?: -1)
                putInt(KEY_NUM_TELEFONO, cuenta.numTelefono ?: -1)
                putInt(KEY_TIPO_PERFIL, cuenta.tipoPerfil?.toInt() ?: -1)
                putString(KEY_URL_FOTO_PERFIL, cuenta.urlFotoPerfil)
                putString(KEY_USERNAME, cuenta.username)
                apply()
            }
        }

        fun getLoggedInAccount(context: Context): Cuenta? {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val email = sharedPreferences.getString(KEY_EMAIL, null)
            val passwd = sharedPreferences.getString(KEY_PASSWORD, null)
            val idCuenta = sharedPreferences.getInt(KEY_ID_CUENTA, -1)
            val apellidoDos = sharedPreferences.getString(KEY_APELLIDO_DOS, null)
            val apellidoUno = sharedPreferences.getString(KEY_APELLIDO_UNO, null)
            val nombre = sharedPreferences.getString(KEY_NOMBRE, null)
            val numMovil = sharedPreferences.getInt(KEY_NUM_MOVIL, -1)
            val numTelefono = sharedPreferences.getInt(KEY_NUM_TELEFONO, -1)
            val tipoPerfil = sharedPreferences.getInt(KEY_TIPO_PERFIL, -1).toByte()
            val urlFotoPerfil = sharedPreferences.getString(KEY_URL_FOTO_PERFIL, null)
            val username = sharedPreferences.getString(KEY_USERNAME, null)

            return if (email != null && passwd != null) {
                Cuenta(
                    idCuenta = if (idCuenta != -1) idCuenta else null,
                    email = email,
                    passwd = passwd,
                    apellidoDos = apellidoDos,
                    apellidoUno = apellidoUno,
                    nombre = nombre,
                    numMovil = if (numMovil != -1) numMovil else null,
                    numTelefono = if (numTelefono != -1) numTelefono else null,
                    tipoPerfil = if (tipoPerfil != (-1).toByte()) tipoPerfil else null,
                    urlFotoPerfil = urlFotoPerfil,
                    username = username
                )
            } else {
                null
            }
        }
    }
    }
