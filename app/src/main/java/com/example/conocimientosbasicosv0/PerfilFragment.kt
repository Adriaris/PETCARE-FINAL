package com.example.conocimientosbasicosv0

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class PerfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla el layout para este fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }

        // instancia de SessionManager
        val sessionManager = SessionManager(requireContext())
        val cuenta = sessionManager.getLoggedInAccount()

        cuenta?.let {
            // TextViews con los datos de la cuenta
            view.findViewById<TextView>(R.id.textViewNombre).text = "${it.nombre ?: ""} ${it.apellidoUno ?: ""} ${it.apellidoDos ?: ""}".trim()
            view.findViewById<TextView>(R.id.textViewEmail).text = it.email ?: ""
            view.findViewById<TextView>(R.id.textViewNumMovil).text = "Número Móvil: ${it.numMovil ?: "N/A"}"
            view.findViewById<TextView>(R.id.textViewNumTelefono).text = "Número Teléfono: ${it.numTelefono ?: "N/A"}"
            view.findViewById<TextView>(R.id.textViewTipoPerfil).text = "Tipo de Perfil: ${it.tipoPerfil ?: "N/A"}"
            view.findViewById<TextView>(R.id.textViewApellidoUno).text = "Apellido 1: ${it.apellidoUno ?: "N/A"}"
            view.findViewById<TextView>(R.id.textViewApellidoDos).text = "Apellido 2: ${it.apellidoDos ?: "N/A"}"
            view.findViewById<TextView>(R.id.textViewUsername).text = "Nombre de Usuario: ${it.username ?: "N/A"}"

            //
        }
    }

    private fun cerrarSesion() {
        // Limpia los datos de sesión
        SessionManager(requireContext()).clearSession()

        // Crea un Intent para iniciar LoginActivity
        val intent = Intent(activity, LoginActivity::class.java)

        // Limpiar la pila de actividades para evitar que el usuario regrese a la pantalla de perfil
        // después de haber cerrado sesión y ser redirigido al login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // Inicia LoginActivity
        startActivity(intent)

        activity?.finish()
    }
}

