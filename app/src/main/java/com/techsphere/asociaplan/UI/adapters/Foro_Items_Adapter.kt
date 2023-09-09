package com.techsphere.asociaplan.UI.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.models.ForoItem
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch



class Foro_Items_Adapter (private val dataSet: MutableList<ForoItem>) :
    RecyclerView.Adapter<Foro_Items_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView
        val btnAmpliar: Button
        val vista: Context
        var id = 0

        init {
            txtTitulo = view.findViewById(R.id.txt_nombre)
            btnAmpliar = view.findViewById(R.id.button_see_foro)
            this.vista = view.context

            btnAmpliar.setOnClickListener {
                val context = this.vista
                CoroutineScope(IO).launch {
                    // Como no encontre alguna manera de actualizar los datos
                    // decidi que seria mejor volver a iniciar la actividad
                    val intent = Intent(context, message_forum::class.java)
                    intent.putExtra("id", id.toInt())
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_foro,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var Foro: ForoItem = dataSet.get(position)
        holder.txtTitulo.text = Foro.getTitulo()
        holder.id = Foro.getidMensaje()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}