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
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.view.evaluar_evento
import com.techsphere.asociaplan.view.eventos_evaluar

class Eventos_Evaluar_Adapter (private val dataSet: MutableList<Eventos>) :
    RecyclerView.Adapter<Eventos_Evaluar_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtFecha: TextView
        val btnEnvaluar: Button
        val vista: Context
        var id = 0
        var nombre = ""

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtFecha = view.findViewById(R.id.txt_date)
            btnEnvaluar = view.findViewById(R.id.button_evaluar)
            this.vista = view.context
            btnEnvaluar.setOnClickListener {
                val intent = Intent(view.context, evaluar_evento::class.java)
                intent.putExtra("id",id.toInt())
                intent.putExtra("nombre",nombre.toString())
                this.vista.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_eventos_evaluar,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var even: Eventos = dataSet.get(position)
        holder.txtNombre.text = even.getTitulo().toString()
        holder.txtFecha.text = even.getFecha().toString()
        holder.id = even.getId()
        holder.nombre = even.getTitulo()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}