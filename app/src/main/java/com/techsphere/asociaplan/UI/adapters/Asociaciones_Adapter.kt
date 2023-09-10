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
import com.techsphere.asociaplan.view.ver_miembros
import com.techsphere.asociaplan.view.agregar_miembros
import com.techsphere.asociaplan.view.asociaciones
import com.techsphere.asociaplan.view.editar_asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Asociaciones_Adapter (private val dataSet: MutableList<Asociacion>) :
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
        var id = 0
        var nombre = ""

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
                intent.putExtra("nombre", nombre.toString())
                intent.putExtra("id", id.toInt())
                this.vista.startActivity(intent)
            }
            btnEliminar.setOnClickListener {
                eliminarAsociacion()
            }
            btnMiembros.setOnClickListener {
                val intent = Intent(view.context,ver_miembros::class.java)
                intent.putExtra("id", id.toInt())
                this.vista.startActivity(intent)
            }
            btnAgregar.setOnClickListener {
                val intent = Intent(view.context,agregar_miembros::class.java)
                intent.putExtra("id", id.toInt())
                this.vista.startActivity(intent)
            }
        }

        fun eliminarAsociacion() {
            val dialog = AlertDialog.Builder(vista)
            val inflater = LayoutInflater.from(vista)
            val view = inflater.inflate(R.layout.dialog_delete_asociacion, null)
            dialog.setView(view)
            dialog.setCancelable(true)
            val btnDelete = view.findViewById<Button>(R.id.eliminarAsociacionBoton)
            val btnCancel = view.findViewById<Button>(R.id.cancelarEliminarAsociacionBoton)
            val d = dialog.create()
            d.show()
            btnDelete.setOnClickListener {
                deleteAsociacionBD(id)
                d.dismiss()
                val intent = Intent(view.context,asociaciones::class.java)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_asociacion,
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