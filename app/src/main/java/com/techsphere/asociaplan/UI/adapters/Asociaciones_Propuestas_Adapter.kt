package com.techsphere.asociaplan.UI.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.controller.deleteAsociacionBD
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.view.agregar_miembros
import com.techsphere.asociaplan.view.asociaciones
import com.techsphere.asociaplan.view.editar_asociacion
import com.techsphere.asociaplan.view.enviar_propuesta
import com.techsphere.asociaplan.view.menu_estudiante

class Asociaciones_Propuestas_Adapter (private val dataSet: MutableList<Asociacion>) :
    RecyclerView.Adapter<Asociaciones_Propuestas_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombreAsociacion: TextView
        val txtCodigo: TextView
        val txtContacto: TextView
        val btnEnviar: Button
        val vista: Context
        var id = 0
        var nombre = ""

        init {
            txtNombreAsociacion = view.findViewById(R.id.txt_nombre)
            txtCodigo = view.findViewById(R.id.txt_cod_carrera)
            txtContacto = view.findViewById(R.id.txt_contacto)
            btnEnviar = view.findViewById(R.id.button_enviar)
            this.vista = view.context
            btnEnviar.setOnClickListener {
                val intent = Intent(view.context, enviar_propuesta::class.java)
                intent.putExtra("id",id.toInt())
                this.vista.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_asociacion_propuesta,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var asoc: Asociacion = dataSet.get(position)
        holder.txtCodigo.text = asoc.getCodigo()
        holder.txtNombreAsociacion.text = asoc.getNombre()
        holder.txtContacto.text = asoc.getContacto()
        holder.id = asoc.getId()
        holder.nombre = asoc.getNombre()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}