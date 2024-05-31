package com.example.conocimientosbasicosv0.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.model.Cuidador

class FavoritosAdapter(private val cuidadoresList: List<Cuidador>) : RecyclerView.Adapter<FavoritosAdapter.CuidadorViewHolder>() {

    class CuidadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val apellidoTextView: TextView = itemView.findViewById(R.id.apellidoTextView)
        val descripcionCortaTextView: TextView = itemView.findViewById(R.id.descripcionCortaTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuidadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cuidador_favorito, parent, false)
        return CuidadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuidadorViewHolder, position: Int) {
        val cuidador = cuidadoresList[position]
        holder.nombreTextView.text = cuidador.nombre
        holder.apellidoTextView.text = "${cuidador.apellido1} ${cuidador.apellido2}"
        holder.descripcionCortaTextView.text = cuidador.descripcionCorta
    }

    override fun getItemCount(): Int {
        return cuidadoresList.size
    }
}
