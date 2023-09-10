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
import com.techsphere.asociaplan.controller.deleteMiembroBD
import com.techsphere.asociaplan.models.Miembro
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class Ver_Miembros_Adapter (private val dataSet: MutableList<Miembro>) :
    RecyclerView.Adapter<Ver_Miembros_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtCarne: TextView
        var Id = 0
        var IdAsociacion = 0
        val btnEliminar: Button
        val vista: Context

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtCarne = view.findViewById(R.id.txt_carne)
            btnEliminar = view.findViewById(R.id.button_eliminar_miembro)
            this.vista = view.context

            btnEliminar.setOnClickListener {
                val dialog = AlertDialog.Builder(vista)
                val inflater = LayoutInflater.from(vista)
                val view = inflater.inflate(R.layout.dialog_delete_miembro, null)
                dialog.setView(view)
                dialog.setCancelable(true)
                val btnDelete = view.findViewById<Button>(R.id.eliminarMiembroBoton)
                val btnCancel = view.findViewById<Button>(R.id.cancelarEliminarMiembroBoton)
                val d = dialog.create()
                d.show()
                btnDelete.setOnClickListener {
                    deleteMiembroBD(Id)
                    d.dismiss()
                    val intent = Intent(view.context, ver_miembros::class.java)
                    intent.putExtra("id", IdAsociacion)
                    this.vista.startActivity(intent)
                    (this.vista as Activity).finish()
                }
                btnCancel.setOnClickListener {
                    d.dismiss()
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_miembro,
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