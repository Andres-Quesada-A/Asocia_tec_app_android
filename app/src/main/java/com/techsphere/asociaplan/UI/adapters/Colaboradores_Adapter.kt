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
//import com.techsphere.asociaplan.controller.eliminarApartadoBD
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Estudiantes
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Colaboradores_Adapter (private val dataSet: Array<Estudiantes>) :
    RecyclerView.Adapter<Colaboradores_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtContacto: TextView
        val btnAgregar: Button
        val btnEliminar: Button
        val btnEditar: Button
        val vista: Context
        var correo = ""

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtContacto = view.findViewById(R.id.txt_contacto)
            btnAgregar = view.findViewById(R.id.button_agregar_miembro)
            btnEditar = view.findViewById(R.id.button_editar)
            btnEliminar = view.findViewById(R.id.button_eliminar)
            this.vista = view.context
            btnEditar.setOnClickListener {
                val intent = Intent(view.context,edit_collaborator::class.java)
                intent.putExtra("nombre", txtNombre.toString())
                this.vista.startActivity(intent)
            }
            btnEliminar.setOnClickListener {
                eliminarAsociacion()
            }
            btnAgregar.setOnClickListener {
                //val intent = Intent(view.context,agregar_miembros::class.java)
                //intent.putExtra("correo", correo.toString())
                //this.vista.startActivity(intent)
            }
        }

        fun eliminarAsociacion() {
            ///
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_collaborator,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var asoc: Estudiantes = dataSet.get(position)
        holder.txtNombre.text = asoc.getNombre()
        holder.txtContacto.text = asoc.getContacto()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}