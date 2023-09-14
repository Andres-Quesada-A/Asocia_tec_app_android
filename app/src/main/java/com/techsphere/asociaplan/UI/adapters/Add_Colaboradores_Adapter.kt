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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.controller.deleteColaboradorBD
import com.techsphere.asociaplan.controller.gestionColaborador
import com.techsphere.asociaplan.models.Colaborador
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Add_Colaboradores_Adapter (private val dataSet: MutableList<Colaborador>,
                                 private val eventId: Int,
                                 private val activityId: Int,
                                private val isEvent: Boolean=false):
    RecyclerView.Adapter<Add_Colaboradores_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtContacto: TextView
        val btnVerDetalles: Button
        val btnAdd: Button
        var isEvent: Boolean = false
        val vista: Context
        var idColaborador = 0
        var idEvento = 0
        var idActividad = 0
        var nombre = ""

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtContacto = view.findViewById(R.id.txt_contacto)
            btnVerDetalles = view.findViewById(R.id.button_ver_miembros)
            btnAdd = view.findViewById(R.id.button_add)
            this.vista = view.context
            btnVerDetalles.setOnClickListener {
                val intent = Intent(view.context,collaborator_details::class.java)
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
            btnAdd.setOnClickListener {
                addColaborator()
            }
        }
        private fun addColaborator(){
            val diag = dialogs(vista)
            var tipoGestion: Int = 0
            if (isEvent==true) {
                tipoGestion=3
            } else{
                tipoGestion=4
            }
            val load = diag.showLoadingDialog()
            load.show()
            CoroutineScope(Dispatchers.IO).launch {
                val res = gestionColaborador(0, idEvento, idActividad, tipoGestion,
                    idColaborador)
                withContext(Dispatchers.Main){
                    load.dismiss()
                    if (res==1){
                        diag.showSuccessDialog(28)
                    } else{
                        diag.showErrorDialog(28, false)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_add_collaborator,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var asoc: Colaborador = dataSet.get(position)
        holder.txtNombre.text = "Nombre: ${asoc.getNombre()}"
        holder.txtContacto.text = "Contacto: ${asoc.getContacto()}"
        holder.idColaborador = asoc.getid()
        holder.idEvento = eventId
        holder.idActividad = activityId
        holder.nombre = asoc.getNombre()
        holder.isEvent=isEvent
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}