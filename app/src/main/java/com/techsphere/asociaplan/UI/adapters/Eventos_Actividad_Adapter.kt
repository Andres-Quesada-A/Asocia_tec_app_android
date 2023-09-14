package com.techsphere.asociaplan.UI.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.models.Eventos_Asociacion
import com.techsphere.asociaplan.view.actividades
import com.techsphere.asociaplan.view.informe

class Eventos_Actividad_Adapter (private val dataSet: MutableList<Eventos_Asociacion>) :
    RecyclerView.Adapter<Eventos_Actividad_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView
        val txtFecha: TextView
        val txtCategoria: TextView
        val btnActividades: Button
        val vista: Context
        var id = 0
        var nombre = ""

        init {
            txtTitulo = view.findViewById(R.id.txt_title)
            txtFecha = view.findViewById(R.id.txt_date)
            txtCategoria = view.findViewById(R.id.txt_category)
            btnActividades = view.findViewById(R.id.button_actividades)
            this.vista = view.context
            btnActividades.setOnClickListener {
                val intent = Intent(view.context, actividades::class.java)
                intent.putExtra("id", id.toInt())
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_eventos_actividad,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var eventos: Eventos_Asociacion = dataSet.get(position)
        holder.txtTitulo.text = "Titulo: ${eventos.getTitulo()}"
        holder.txtCategoria.text = "Categoria: ${eventos.getCategoria()}"
        holder.txtFecha.text="Fecha: ${eventos.getFecha()}"
        holder.id = eventos.getId()
        holder.nombre = eventos.getTitulo()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}