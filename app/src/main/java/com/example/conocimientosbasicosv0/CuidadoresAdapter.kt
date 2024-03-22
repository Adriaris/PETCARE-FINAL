package com.example.conocimientosbasicosv0

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CuidadoresAdapter(private val cuidadores: List<Cuidador>) : RecyclerView.Adapter<CuidadoresAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.nombreTextView)
        val apellido1TextView: TextView = view.findViewById(R.id.apellido1TextView)
        val apellido2TextView: TextView = view.findViewById(R.id.apellido2TextView)
        val idCuidadorTextView: TextView = view.findViewById(R.id.idCuidadorTextView)

        fun bind(cuidador: Cuidador) {
            nombreTextView.text = cuidador.nombre
            apellido1TextView.text = cuidador.apellido1
            apellido2TextView.text = cuidador.apellido2
            idCuidadorTextView.text = cuidador.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_cuidador, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuidador = cuidadores[position]
        holder.bind(cuidador)
    }

    override fun getItemCount(): Int = cuidadores.size
}
