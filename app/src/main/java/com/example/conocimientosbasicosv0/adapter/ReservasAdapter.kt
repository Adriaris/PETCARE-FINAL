package com.example.conocimientosbasicosv0.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conocimientosbasicosv0.R
import com.example.conocimientosbasicosv0.model.Reserva

class ReservasAdapter(private var reservas: List<Reserva>) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNombreReserva: TextView = view.findViewById(R.id.textViewNombreReserva)
        val textViewDetalles: TextView = view.findViewById(R.id.textViewDetalles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return ReservaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservas[position]
        holder.textViewNombreReserva.text = reserva.nombreReserva
        holder.textViewDetalles.text = reserva.detalles.joinToString(", ")
    }

    override fun getItemCount(): Int = reservas.size

    fun updateReservas(newReservas: List<Reserva>) {
        reservas = newReservas
        notifyDataSetChanged()
    }
}



