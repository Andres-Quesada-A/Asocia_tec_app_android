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
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.deleteEventoBD
import com.techsphere.asociaplan.models.Eventos_Asociacion
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class Eventos_Adapter (private val dataSet: MutableList<Eventos_Asociacion>) :
    RecyclerView.Adapter<Eventos_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView
        val txtFecha: TextView
        val txtCategoria: TextView
        val btnDetalles: Button
        val btnEliminar: Button
        val btnEditar: Button
        val vista: Context
        lateinit var event: Eventos_Asociacion
        var id = 0
        var nombre = ""

        init {
            txtTitulo = view.findViewById(R.id.txt_title)
            txtFecha = view.findViewById(R.id.txt_date)
            txtCategoria = view.findViewById(R.id.txt_category)
            btnDetalles = view.findViewById(R.id.button_details)
            btnEditar = view.findViewById(R.id.button_editar)
            btnEliminar = view.findViewById(R.id.button_eliminar)
            this.vista = view.context
            btnEditar.setOnClickListener {
                if (AuthHelper(vista).getAccountType()==3){
                    val intent = Intent(view.context,edit_event::class.java)
                    intent.putExtra("id", id.toInt())
                    intent.putExtra("nombre", nombre.toString())
                    this.vista.startActivity(intent)
                } else{
                    val intent = Intent(vista, EditEventAdminActivity::class.java)
                    intent.putExtra("Evento",event )
                    vista.startActivity(intent)
                }
            }
            btnEliminar.setOnClickListener {
                eliminarEvento()
            }
            btnDetalles.setOnClickListener {
                val intent = Intent(view.context,event_details::class.java)
                if (AuthHelper(vista).getAccountType()==3){
                    intent.putExtra("id", id.toInt())
                    intent.putExtra("nombre", nombre.toString())
                } else{
                    intent.putExtra("Evento",event )
                }
                vista.startActivity(intent)
            }
        }

        fun eliminarEvento() {
            val dialog = AlertDialog.Builder(vista)
            val inflater = LayoutInflater.from(vista)
            val view = inflater.inflate(R.layout.dialog_delete_evento, null)
            dialog.setView(view)
            dialog.setCancelable(true)
            val btnDelete = view.findViewById<Button>(R.id.eliminarEventoBoton)
            val btnCancel = view.findViewById<Button>(R.id.cancelarEliminarEventoBoton)
            val d = dialog.create()
            d.show()
            btnDelete.setOnClickListener {
                deleteEventoBD(id, txtTitulo.toString(), txtFecha.toString())
                d.dismiss()
                var intent: Intent
                if (AuthHelper(vista).getAccountType()==3){
                    intent = Intent(view.context,events::class.java)
                } else{
                    intent = Intent(view.context,EventsAdminActivity::class.java)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_event,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var eventos: Eventos_Asociacion = dataSet.get(position)
        holder.txtTitulo.text = "Titulo: ${eventos.getTitulo()}"
        holder.txtCategoria.text = "Categoria: ${eventos.getCategoria()}"
        holder.txtFecha.text="Fecha: ${eventos.getFecha()}"
        holder.id = eventos.getId()
        holder.nombre = eventos.getTitulo()
        holder.event=eventos
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}