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
import com.techsphere.asociaplan.view.RegisterAsociationActivity
import com.techsphere.asociaplan.view.agregar_miembros
import com.techsphere.asociaplan.view.asociaciones
import com.techsphere.asociaplan.view.editar_asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Asociaciones_Adapter (private val dataSet: Array<Asociacion>) :
    RecyclerView.Adapter<Asociaciones_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombreAsociacion: TextView
        val txtCodigo: TextView
        val txtContacto: TextView
        val btnMiembros: Button
        val btnAgregar: Button
        val btnEliminar: Button
        val btnEditar: Button
        val vista: Context
        var correo = ""

        init {
            txtNombreAsociacion = view.findViewById(R.id.txt_nombre)
            txtCodigo = view.findViewById(R.id.txt_cod_carrera)
            txtContacto = view.findViewById(R.id.txt_contacto)
            btnAgregar = view.findViewById(R.id.button_agregar_miembro)
            btnEditar = view.findViewById(R.id.button_editar)
            btnEliminar = view.findViewById(R.id.button_eliminar)
            btnMiembros = view.findViewById(R.id.button_ver_miembros)
            this.vista = view.context
            btnEditar.setOnClickListener {
                val intent = Intent(view.context,editar_asociacion::class.java)
                intent.putExtra("nombre", txtNombreAsociacion.toString())
                this.vista.startActivity(intent)
            }
            btnEliminar.setOnClickListener {
                eliminarAsociacion()
            }
            btnMiembros.setOnClickListener {
                VerMiembros()
            }
            btnAgregar.setOnClickListener {
                val intent = Intent(view.context,agregar_miembros::class.java)
                intent.putExtra("correo", correo.toString())
                this.vista.startActivity(intent)
            }
        }

        fun eliminarAsociacion() {
            ///
        }

        fun VerMiembros() {
            ///
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_asociacion,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var asoc: Asociacion = dataSet.get(position)
        holder.txtCodigo.text = asoc.getCodigo()
        holder.txtNombreAsociacion.text = asoc.getNombre()
        holder.txtContacto.text = asoc.getContacto()
        holder.correo = asoc.getCorreo()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}