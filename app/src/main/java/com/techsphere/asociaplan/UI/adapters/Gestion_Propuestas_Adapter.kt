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
import com.techsphere.asociaplan.models.Propuesta
import com.techsphere.asociaplan.view.gestion_propuestas
import com.techsphere.asociaplan.view.informacion_propuesta
import com.techsphere.asociaplan.view.menu_estudiante

class Gestion_Propuestas_Adapter (private val dataSet: MutableList<Propuesta>) :
    RecyclerView.Adapter<Gestion_Propuestas_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTematica: TextView
        val txtPromotor: TextView
        val txtEstado: TextView
        val btnDetalles: Button
        val vista: Context
        var id = 0

        init {
            txtTematica = view.findViewById(R.id.txt_tematica)
            txtPromotor = view.findViewById(R.id.txt_promotor)
            txtEstado = view.findViewById(R.id.txt_estado)
            btnDetalles = view.findViewById(R.id.button_informacion)
            this.vista = view.context
            btnDetalles.setOnClickListener {
                val intent = Intent(view.context, informacion_propuesta::class.java)
                intent.putExtra("id", id.toInt())
                this.vista.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_propuestas,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var prop: Propuesta = dataSet.get(position)
        holder.txtTematica.text = prop.getTematica()
        holder.txtPromotor.text = prop.getPromotor()
        holder.txtEstado.text = prop.getEstado()
        holder.id = prop.getId()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}