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
import com.techsphere.asociaplan.controller.AgregarEstudianteBD
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Estudiantes
import com.techsphere.asociaplan.view.RegisterAsociationActivity
import com.techsphere.asociaplan.view.agregar_miembros
import com.techsphere.asociaplan.view.asociaciones
import com.techsphere.asociaplan.view.editar_asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class Agregar_Miembros_Adapter (private val dataSet: MutableList<Estudiantes>) :
    RecyclerView.Adapter<Agregar_Miembros_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtCodigo: TextView
        val txtContacto: TextView
        val btnAgregar: Button
        val vista: Context

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtCodigo = view.findViewById(R.id.txt_cod_carrera)
            txtContacto = view.findViewById(R.id.txt_contacto)
            btnAgregar = view.findViewById(R.id.button_agregar_miembro)
            this.vista = view.context

            btnAgregar.setOnClickListener {
                val context = this.vista
                val codigo = txtCodigo
                CoroutineScope(IO).launch {
                    // Como no encontre alguna manera de actualizar los datos
                    // decidi que seria mejor volver a iniciar la actividad
                    AgregarEstudianteBD(codigo.toString())
                    val intent = Intent(context, asociaciones::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
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
        var asoc: Estudiantes = dataSet.get(position)
        holder.txtCodigo.text = asoc.getCodigo()
        holder.txtNombre.text = asoc.getNombre()
        holder.txtContacto.text = asoc.getContacto()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}