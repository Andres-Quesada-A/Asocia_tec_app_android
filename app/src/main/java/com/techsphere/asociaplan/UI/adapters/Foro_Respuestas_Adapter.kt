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
import com.techsphere.asociaplan.models.ForoRespuesta
import com.techsphere.asociaplan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch



class Foro_Respuestas_Adapter (private val dataSet: MutableList<ForoRespuesta>) :
    RecyclerView.Adapter<Foro_Respuestas_Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRespuesta: TextView
        val txtAutor: TextView
        init {
            txtRespuesta = view.findViewById(R.id.txt_respuesta)
            txtAutor= view.findViewById(R.id.txtAutor)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_forum_replies,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var Foro: ForoRespuesta = dataSet.get(position)
        holder.txtRespuesta.text = Foro.getCuerpo()
        holder.txtAutor.text = "${Foro.getAutor()} respondio:"
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}