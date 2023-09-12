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
import com.techsphere.asociaplan.view.EditEventCapacityActivity
import com.techsphere.asociaplan.view.EventInfoActivity
import com.techsphere.asociaplan.view.evaluar_evento
import com.techsphere.asociaplan.view.eventos_evaluar

class Eventos_Capacidad_Adapter (private val dataSet: MutableList<Eventos>) :
    RecyclerView.Adapter<Eventos_Capacidad_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtFecha: TextView
        val btnGestionar: Button
        val context: Context
        lateinit var evento: Eventos

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtFecha = view.findViewById(R.id.txt_date)
            btnGestionar = view.findViewById(R.id.button_gestionar)
            this.context = view.context
            btnGestionar.setOnClickListener {
                showMoreInfo()
            }
        }
        private fun showMoreInfo(){
            val intent = Intent(context, EditEventCapacityActivity::class.java)
            intent.putExtra("Evento", evento)
            //Iniciamos la actividad pero no cerramos la del calendario
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_eventos_capacidad,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var even: Eventos = dataSet.get(position)
        holder.txtNombre.text = even.getTitulo().toString()
        holder.txtFecha.text = even.getFecha().toString()
        holder.evento=even
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}