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


class Colaboradores_Adapter (private val dataSet: MutableList<Colaborador>,
                             private val eventId: Int=0,
                             private val activityId: Int=0,
                             private val isEvent: Boolean=false,
                             private val isInEventOrActivity: Boolean= false) :
    RecyclerView.Adapter<Colaboradores_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView
        val txtContacto: TextView
        val btnVerDetalles: Button
        val btnEliminar: Button
        val btnEditar: Button
        val vista: Context
        var id = 0
        var idEvento = 0
        var idActivity=0
        var isEvent = false
        var isInEventOrActivity = false
        var nombre = ""

        init {
            txtNombre = view.findViewById(R.id.txt_nombre)
            txtContacto = view.findViewById(R.id.txt_contacto)
            btnVerDetalles = view.findViewById(R.id.button_ver_miembros)
            btnEditar = view.findViewById(R.id.button_editar)
            btnEliminar = view.findViewById(R.id.button_eliminar)
            this.vista = view.context
            btnEditar.setOnClickListener {
                val intent = Intent(view.context,edit_collaborator::class.java)
                intent.putExtra("id", id.toInt())
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
            btnEliminar.setOnClickListener {
                if (isInEventOrActivity){
                    eliminarColaboradorActividadOEvento()
                } else{
                    eliminarColaborador()
                }
            }
            btnVerDetalles.setOnClickListener {
                val intent = Intent(view.context,collaborator_details::class.java)
                intent.putExtra("nombre", nombre.toString())
                this.vista.startActivity(intent)
            }
        }

        fun eliminarColaborador() {
            val dialog = AlertDialog.Builder(vista)
            val inflater = LayoutInflater.from(vista)
            val view = inflater.inflate(R.layout.dialog_delete_colaborador, null)
            dialog.setView(view)
            dialog.setCancelable(true)
            val btnDelete = view.findViewById<Button>(R.id.eliminarColaboradorBoton)
            val btnCancel = view.findViewById<Button>(R.id.cancelarEliminarColaboradorBoton)
            val d = dialog.create()
            d.show()
            btnDelete.setOnClickListener {
                deleteColaboradorBD(id)
                d.dismiss()
                val intent = Intent(view.context,collaborator_list::class.java)
                this.vista.startActivity(intent)
                (this.vista as Activity).finish()
            }
            btnCancel.setOnClickListener {
                d.dismiss()
            }
        }
        fun eliminarColaboradorActividadOEvento(){
            val diag = dialogs(vista)
            var tipoGestion: Int = 0
            if (isEvent==true) {
                tipoGestion=1
            } else{
                tipoGestion=2
            }
            val load = diag.showLoadingDialog()
            load.show()
            CoroutineScope(Dispatchers.IO).launch {
                val res = gestionColaborador(0, idEvento, idActivity, tipoGestion,
                    id)
                withContext(Dispatchers.Main){
                    load.dismiss()
                    if (res==1){
                        diag.showSuccessDialog(29)
                    } else{
                        diag.showErrorDialog(29, false)
                    }
                }
            }
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
        var asoc: Colaborador = dataSet.get(position)
        holder.txtNombre.text = asoc.getNombre()
        holder.txtContacto.text = asoc.getContacto()
        holder.id = asoc.getid()
        holder.nombre = asoc.getNombre()
        if (isInEventOrActivity){
            holder.isInEventOrActivity=isInEventOrActivity
            holder.isEvent=isEvent
            holder.idActivity=activityId
            holder.idEvento=eventId
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}