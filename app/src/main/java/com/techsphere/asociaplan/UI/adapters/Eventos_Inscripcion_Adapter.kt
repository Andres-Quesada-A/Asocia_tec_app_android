package com.techsphere.asociaplan.UI.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.Administrador
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.utils.EmailSender
import com.techsphere.asociaplan.view.EventInfoActivity
import com.techsphere.asociaplan.view.EventInscriptionActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Eventos_Inscripcion_Adapter(private val eventos: MutableList<Eventos>):
        RecyclerView.Adapter<Eventos_Inscripcion_Adapter.ViewHolder>(){
            class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val nombreText : TextView
                val fechaText: TextView
                val duracionText: TextView
                val moreInfoButton: Button
                val confirmButton: Button
                val cancelButton: Button
                val context = view.context
                lateinit var evento: Eventos
                private var dialog = dialogs(view.context)
                init {
                    nombreText=view.findViewById(R.id.txt_title)
                    fechaText=view.findViewById(R.id.txt_date)
                    duracionText=view.findViewById(R.id.txt_category)
                    moreInfoButton=view.findViewById(R.id.button_details)
                    confirmButton=view.findViewById(R.id.button_confirm_inscription)
                    cancelButton=view.findViewById(R.id.button_cancel_inscription)
                    moreInfoButton.setOnClickListener {
                        showMoreInfo()
                    }
                    confirmButton.setOnClickListener {
                        confirmAsistance()
                    }
                    cancelButton.setOnClickListener {
                        cancelInscription()
                    }
                }
                private fun showMoreInfo(){
                    val intent = Intent(context, EventInfoActivity::class.java)
                    intent.putExtra("Evento", evento)
                    //Iniciamos la actividad pero no cerramos la del calendario
                    context.startActivity(intent)
                }
                private fun confirmAsistance(){
                    if (evento.getEstado()==2){
                        val admin = Administrador()
                        val cargaDialog = dialog.showLoadingDialog()
                        val authHelper = AuthHelper(context)
                        var res: Int
                        cargaDialog.show()
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.IO){
                                res = admin.confirmAssistance(authHelper.getAccountId(), evento.getId())
                            }
                            cargaDialog.dismiss()
                            if (res==1){
                                dialog.showSuccessDialog(20, false)
                                confirmButton.isClickable=false
                                confirmButton.backgroundTintList=context.getColorStateList(R.color.gray)
                                evento.setEstado(3)
                            } else{
                                dialog.showErrorDialog(20, false)
                            }
                        }
                    }
                }
                private fun cancelInscription(){
                    if (evento.getEstado()==2||evento.getEstado()==3){
                        val admin = Administrador()
                        val cargaDialog = dialog.showLoadingDialog()
                        val authHelper = AuthHelper(context)
                        var res: Int = 1
                        cargaDialog.show()
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.IO){
                                res = admin.cancelAssistance(authHelper.getAccountId(), evento.getId())
                            }
                            cargaDialog.dismiss()
                            if (res==1){
                                val emailSender = EmailSender()
                                val contentEmail = "Ha cancelado su inscripción al evento ${evento.getTitulo()} que se realizará el día ${evento.getFecha()}"
                                val email = authHelper.getAccountEmail()
                                emailSender.sendEmail(email, "Inscripción cancelada", contentEmail)
                                dialog.showSuccessDialog(21, false)
                                cancelButton.isClickable=false
                                cancelButton.backgroundTintList=context.getColorStateList(R.color.gray)
                                confirmButton.isClickable=false
                                confirmButton.backgroundTintList=context.getColorStateList(R.color.gray)
                                evento.setEstado(1)
                            } else{
                                dialog.showErrorDialog(21, false)
                            }
                        }
                    }
                }
                fun updateButton(){
                    if (evento.getEstado()==3){
                        confirmButton.isClickable=false
                        confirmButton.backgroundTintList=context.getColorStateList(R.color.gray)
                    }
                }

            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evento: Eventos = eventos.get(position)
        holder.evento=evento
        holder.nombreText.text="Nombre: ${evento.getTitulo()}"
        holder.duracionText.text="Duracion: ${evento.getDuracion()} horas"
        holder.fechaText.text="Fecha: ${evento.getFecha()}"
        holder.updateButton()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            Eventos_Inscripcion_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_event_inscription, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventos.size
    }
}