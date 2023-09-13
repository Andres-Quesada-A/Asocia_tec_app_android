package com.techsphere.asociaplan.UI.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.models.Evaluacion
import com.techsphere.asociaplan.models.Eventos_Asociacion
import com.techsphere.asociaplan.view.informe

class Evaluaciones_Evento_Adapter (private val dataSet: MutableList<Evaluacion>) :
    RecyclerView.Adapter<Evaluaciones_Evento_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCalificacion: TextView
        val txtComentario: TextView
        val vista: Context
        var id = 0
        var nombre = ""

        init {
            txtCalificacion = view.findViewById(R.id.txt_calificaion)
            txtComentario = view.findViewById(R.id.texto_comentario)
            this.vista = view.context
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_evaluaciones,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var evaluacion: Evaluacion = dataSet.get(position)
        holder.txtCalificacion.text = "Calificacion: ${evaluacion.getCalificacion().toString()}"
        holder.txtComentario.text = evaluacion.getComentario()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}