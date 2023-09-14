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
import com.techsphere.asociaplan.controller.deleteEstudianteBD
import com.techsphere.asociaplan.models.Estudiantes_Admin
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Estudiantes_Adapter (private val dataSet: MutableList<Estudiantes_Admin>) :
    RecyclerView.Adapter<Estudiantes_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtCarne: TextView
        val btnVerDetalles: Button
        val btnEliminar: Button
        val btnEditar: Button
        val vista: Context
        var id = 0
        var nombre = ""

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtCarne = view.findViewById(R.id.txt_carne)
            btnVerDetalles = view.findViewById(R.id.button_ver)
            btnEditar = view.findViewById(R.id.button_editar)
            btnEliminar = view.findViewById(R.id.button_eliminar)
            this.vista = view.context
            btnEditar.setOnClickListener {
                val intent = Intent(view.context,edit_estudiante::class.java)
                intent.putExtra("id", id.toInt())
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
            btnEliminar.setOnClickListener {
                eliminarEstudiante()
            }
            btnVerDetalles.setOnClickListener {
                val intent = Intent(view.context,estudiante_details::class.java)
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
        }

        fun eliminarEstudiante() {
            val dialog = AlertDialog.Builder(vista)
            val inflater = LayoutInflater.from(vista)
            val view = inflater.inflate(R.layout.dialog_delete_estudiante, null)
            dialog.setView(view)
            dialog.setCancelable(true)
            val btnDelete = view.findViewById<Button>(R.id.eliminarEstudianteBoton)
            val btnCancel = view.findViewById<Button>(R.id.cancelarEliminarEstudianteBoton)
            val d = dialog.create()
            d.show()
            btnDelete.setOnClickListener {
                deleteEstudianteBD(id)
                d.dismiss()
                val intent = Intent(view.context,estudiantes_list::class.java)
                this.vista.startActivity(intent)
                (this.vista as Activity).finish()
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_estudiante_admin,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var asoc: Estudiantes_Admin = dataSet.get(position)
        holder.txtNombre.text = asoc.getNombre()
        holder.txtCarne.text = asoc.getCarne().toString()
        holder.id = asoc.getid()
        holder.nombre = asoc.getNombre()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}