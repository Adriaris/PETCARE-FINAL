package com.example.conocimientosbasicosv0.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.conocimientosbasicosv0.R

class ConfiguracionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuracion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val opcion1 = view.findViewById<LinearLayout>(R.id.opcion1)
        val opcion2 = view.findViewById<LinearLayout>(R.id.opcion2)

        opcion1.setOnClickListener {
            // Implementar la acción para la Opción 1
        }

        opcion2.setOnClickListener {
            // Implementar la acción para la Opción 2
        }*/
    }
}
