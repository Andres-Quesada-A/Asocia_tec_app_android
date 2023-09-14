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
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.view.editar_actividad
import com.techsphere.asociaplan.controller.eliminarActividadDBAux
import com.techsphere.asociaplan.models.Actividad
import com.techsphere.asociaplan.view.EventsAdminActivity
import com.techsphere.asociaplan.view.actividades

class Actividades_Adapter (private val dataSet: MutableList<Actividad>,private val idEvento: Int) :
    RecyclerView.Adapter<Actividades_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtFecha: TextView
        val txtUbicacion: TextView
        val btnEditar: Button
        val btnEliminar: Button
        val vista: Context
        var id = 0
        var nombre = ""
        var idEventoH = 0

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtUbicacion = view.findViewById(R.id.txt_ubicacion)
            txtFecha = view.findViewById(R.id.txt_fecha)
            btnEditar = view.findViewById(R.id.button_editar)
            btnEliminar = view.findViewById(R.id.button_eliminar)
            this.vista = view.context
            btnEditar.setOnClickListener {
                val intent = Intent(view.context, editar_actividad::class.java)
                intent.putExtra("id", id.toInt())
                intent.putExtra("idEvento", idEventoH.toInt())
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
            btnEliminar.setOnClickListener {
                eliminarActividad()
            }
        }

        fun eliminarActividad() {
            val dialog = AlertDialog.Builder(vista)
            val inflater = LayoutInflater.from(vista)
            val view = inflater.inflate(R.layout.dialog_delete_actividad, null)
            dialog.setView(view)
            dialog.setCancelable(true)
            val btnDelete = view.findViewById<Button>(R.id.eliminarActividad)
            val btnCancel = view.findViewById<Button>(R.id.cancelarEliminarActividad)
            val d = dialog.create()
            d.show()
            btnDelete.setOnClickListener {
                eliminarActividadDBAux(id)
                d.dismiss()
                var intent: Intent
                if (AuthHelper(vista).getAccountType()==3){
                    intent = Intent(view.context, actividades::class.java)
                    intent.putExtra("id",idEventoH)
                } else{
                    intent = Intent(view.context, EventsAdminActivity::class.java)
                }
                vista.startActivity(intent)
                (vista as Activity).finish()
            }
            btnCancel.setOnClickListener {
                d.dismiss()
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_actividades,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var actividades: Actividad = dataSet.get(position)
        holder.txtNombre.text = "Nombre: ${actividades.getNombre()}"
        holder.txtUbicacion.text = "Ubicacion: ${actividades.getUbicacion()}"
        holder.txtFecha.text="Fecha: ${actividades.getInicio().toString()}"
        holder.id = actividades.getId()
        holder.nombre = actividades.getNombre()
        holder.idEventoH = idEvento
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}