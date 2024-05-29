package com.example.conocimientosbasicosv0.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.data.MascotaInfo

class MascotaUnicaFragment : Fragment() {

    private lateinit var mascota: MascotaInfo

    companion object {
        private const val ARG_MASCOTA = "mascota"

        fun newInstance(mascota: MascotaInfo): MascotaUnicaFragment {
            val fragment = MascotaUnicaFragment()
            val args = Bundle()
            args.putSerializable(ARG_MASCOTA, mascota)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mascota = it.getSerializable(ARG_MASCOTA) as MascotaInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mascota_unica, container, false)

        val nombreTextView = view.findViewById<TextView>(R.id.nombreTextView)
        val razaTextView = view.findViewById<TextView>(R.id.razaTextView)
        val animalImageView = view.findViewById<ImageView>(R.id.animalImageView)
        val edadTextView = view.findViewById<TextView>(R.id.edadTextView)
        val pesoTextView = view.findViewById<TextView>(R.id.pesoTextView)
        val enfermedadesTextView = view.findViewById<TextView>(R.id.enfermedadesTextView)
        val descripcionTextView = view.findViewById<TextView>(R.id.descripcionTextView)

        nombreTextView.text = "Nombre: ${mascota.nombre}"
        razaTextView.text = "Raza: ${mascota.raza}"
        edadTextView.text = "Edad: ${mascota.edad}"
        pesoTextView.text = "Peso: ${mascota.peso}"
        enfermedadesTextView.text = "Enfermedades: ${mascota.enfermedades}"
        descripcionTextView.text = "Descripción: ${mascota.Descripcion}"

        setAnimalImage(animalImageView, mascota.animal)

        return view
    }

    private fun setAnimalImage(imageView: ImageView, animal: String) {
        when (animal) {
            "Perro" -> imageView.setImageResource(R.drawable.perro)
            "Gato" -> imageView.setImageResource(R.drawable.gato)
            "Conejo" -> imageView.setImageResource(R.drawable.conejo)
            "Hámster" -> imageView.setImageResource(R.drawable.hamster)
            "Cobaya" -> imageView.setImageResource(R.drawable.cobaya)
            "Pez" -> imageView.setImageResource(R.drawable.pez)
            "Pájaro" -> imageView.setImageResource(R.drawable.pajaro)
            "Hurón" -> imageView.setImageResource(R.drawable.huron)
            "Tortuga" -> imageView.setImageResource(R.drawable.tortuga)
            "Serpiente" -> imageView.setImageResource(R.drawable.serpiente)
            else -> imageView.setImageResource(android.R.color.transparent)
        }
    }
}
