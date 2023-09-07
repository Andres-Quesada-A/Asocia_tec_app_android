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
import com.techsphere.asociaplan.controller.AgregarMiembroBD
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Miembro
import com.techsphere.asociaplan.view.RegisterAsociationActivity
import com.techsphere.asociaplan.view.agregar_miembros
import com.techsphere.asociaplan.view.asociaciones
import com.techsphere.asociaplan.view.editar_asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class Agregar_Miembros_Adapter (private val dataSet: MutableList<Miembro>) :
    RecyclerView.Adapter<Agregar_Miembros_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtCarne: TextView
        var Id = 0
        var IdAsociacion = 0
        val btnAgregar: Button
        val vista: Context

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtCarne = view.findViewById(R.id.txt_carne)
            btnAgregar = view.findViewById(R.id.button_agregar_miembro)
            this.vista = view.context

            btnAgregar.setOnClickListener {
                val context = this.vista
                CoroutineScope(IO).launch {
                    // Como no encontre alguna manera de actualizar los datos
                    // decidi que seria mejor volver a iniciar la actividad
                    AgregarMiembroBD(Id,IdAsociacion)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_estudiante,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var Miem: Miembro = dataSet.get(position)
        holder.txtCarne.text = Miem.getCarne()
        holder.txtNombre.text = Miem.getNombre()
        holder.Id = Miem.getid()
        holder.IdAsociacion = Miem.getidAsociacion()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}