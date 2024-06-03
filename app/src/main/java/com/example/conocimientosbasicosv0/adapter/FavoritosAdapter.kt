package com.example.conocimientosbasicosv0.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.activity.CuidadorProfileActivity
import com.example.conocimientosbasicosv0.model.Cuidador

class FavoritosAdapter(
    private var cuidadoresList: List<Cuidador>,
    private val eliminarCuidador: (Cuidador) -> Unit
) : RecyclerView.Adapter<FavoritosAdapter.CuidadorViewHolder>() {

    class CuidadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        val descripcionCortaTextView: TextView = itemView.findViewById(R.id.descripcionCortaTextView)
        val verPerfilButton: Button = itemView.findViewById(R.id.verPerfilButton)
        val eliminarButton: Button = itemView.findViewById(R.id.eliminarButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuidadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cuidador_favorito, parent, false)
        return CuidadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuidadorViewHolder, position: Int) {
        val cuidador = cuidadoresList[position]
        holder.nombreTextView.text = cuidador.nombre
        holder.usernameTextView.text = cuidador.username
        holder.descripcionCortaTextView.text = cuidador.descripcionCorta

        holder.verPerfilButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CuidadorProfileActivity::class.java).apply {
                putExtra("username", cuidador.username)
                putExtra("nombre", cuidador.nombre)
                putExtra("apellido1", cuidador.apellido1)
                putExtra("apellido2", cuidador.apellido2)
                putExtra("descripcionCorta", cuidador.descripcionCorta)
                putExtra("descripcionLarga", cuidador.descripcionLarga)
                putExtra("id", cuidador.idCuidador)
            }
            context.startActivity(intent)
        }


        holder.eliminarButton.setOnClickListener {
            eliminarCuidador(cuidador)
        }
    }

    override fun getItemCount(): Int {
        return cuidadoresList.size
    }

    fun updateCuidadoresList(newList: List<Cuidador>) {
        cuidadoresList = newList
        notifyDataSetChanged()
    }
}
