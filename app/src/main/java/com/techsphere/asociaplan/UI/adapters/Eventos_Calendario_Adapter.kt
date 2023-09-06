package com.techsphere.asociaplan.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.models.Eventos

class Eventos_Calendario_Adapter (private val eventos: Array<Eventos>):
        RecyclerView.Adapter<Eventos_Calendario_Adapter.ViewHolder>(){
            class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val nombreText : TextView
                val fechaText: TextView
                val duracionText: TextView
                val moreInfoButton: Button
                val meInteresaButton: Button
                init {
                    nombreText=view.findViewById(R.id.txt_title)
                    fechaText=view.findViewById(R.id.txt_date)
                    duracionText=view.findViewById(R.id.txt_category)
                    moreInfoButton=view.findViewById(R.id.button_details)
                    meInteresaButton=view.findViewById(R.id.button_interest)
                }
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evento: Eventos = eventos.get(position)
        holder.nombreText.text="Nombre: ${evento.getTitulo()}"
        holder.duracionText.text="Duracion: ${evento.getDuracion()} horas"
        holder.fechaText.text="Fecha: ${evento.getFecha()}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            Eventos_Calendario_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_event_calendar, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventos.size
    }
}