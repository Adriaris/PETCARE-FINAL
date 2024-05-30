package com.example.conocimientosbasicosv0.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.model.Reserva

class ReservasAdapter(private var reservasList: List<Reserva>) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val apellidosTextView: TextView = itemView.findViewById(R.id.apellidosTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return ReservaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservasList[position]
        holder.nombreTextView.text = reserva.nombreCuidador
        holder.apellidosTextView.text = "${reserva.apellidoUno} ${reserva.apellidoDos}"
    }

    override fun getItemCount(): Int {
        return reservasList.size
    }

    fun updateReservas(newReservasList: List<Reserva>) {
        reservasList = newReservasList
        notifyDataSetChanged()
    }
}
