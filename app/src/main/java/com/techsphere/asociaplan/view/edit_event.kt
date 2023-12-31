package com.techsphere.asociaplan.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.UI.fragments.DatePickerFragment
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.Administrador
import com.techsphere.asociaplan.controller.EditarEventoInBD
import com.techsphere.asociaplan.controller.getEventosBusqueda
import com.techsphere.asociaplan.models.Colaborador
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.models.Eventos_Asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.time.ZoneId
import java.util.*
import com.techsphere.asociaplan.utils.EmailSender

class edit_event : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtFecha : EditText
    private lateinit var txtLugar : EditText
    private lateinit var txtDuracion : EditText
    private lateinit var txtCategoria : EditText
    private lateinit var txtRequerimientos : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnUpdate : Button
    private var fecha: java.util.Date? =null
    private var Id : Int? = null
    private var IdEvento : Int? = null
    private var NombreEvento : String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        Id = AuthHelper(this).getAccountId()
        IdEvento = (intent?.extras?.getInt("id")) as Int
        NombreEvento = (intent?.extras?.getString("nombre")) as String
        txtNombre = findViewById(R.id.event_name)
        txtFecha = findViewById(R.id.date)
        txtLugar = findViewById(R.id.place)
        txtDuracion = findViewById(R.id.duration)
        txtCategoria = findViewById(R.id.category)
        txtRequerimientos = findViewById(R.id.requirements)
        txtDescripcion = findViewById(R.id.description)
        btnUpdate = findViewById(R.id.button_update)


        cargarEvento()
        txtFecha.setOnClickListener {
            showDatePickerDialog()
        }

        btnUpdate.setOnClickListener {
            Actualizar()
        }
    }
    fun Actualizar() {
        val Nombre = txtNombre.text.toString()
        val Fecha = txtFecha.text.toString()
        val Lugar = txtLugar.text.toString()
        val Duracion = txtDuracion.text.toString()
        val Categoria = txtCategoria.text.toString()
        val Requerimientos = txtRequerimientos.text.toString()
        val Descripcion = txtDescripcion.text.toString()
        val emailSender = EmailSender()

        val contentEmail = "Se ha actualizado el evento ${Nombre}, que se realizará el ${Fecha} en ${Lugar}, que tendrá una duración de ${Duracion}"
        val diag = dialogs(this)
        val load = diag.showLoadingDialog()
        load.show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = EditarEventoInBD(Id!!,IdEvento!!,Nombre,java.sql.Date((fecha as java.util.Date).time),Descripcion,Lugar,Duracion.toInt(),Requerimientos,Categoria)
            if (res == 1){
                // Le pedimos a la BD que nos devuelvan los correos
                val correos = Administrador().getInteresadosEvento(IdEvento!!)
                val formatEmails = correos.joinToString(",")

                // Revisamos si obtuvimos al menos un correo
                if (correos.size!=0){
                    withContext(Dispatchers.Main){
                        load.dismiss()
                    }
                    emailSender.sendEmail(formatEmails, "Actualización del evento", contentEmail)
                    withContext(Dispatchers.Main){
                        diag.showSuccessDialog(23, true)
                    }
                } else {
                    withContext(Dispatchers.Main){
                        load.dismiss()
                        // El numero es arbitrario, ya que es un error generico
                        diag.showErrorDialog(99, false)
                    }
                }
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    load.dismiss()
                    diag.showErrorDialog(23, false)
                }
            }
        }
    }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int){
        txtFecha.setText("$day/${month+1}/$year")
        fecha = GregorianCalendar(year, month, day).getTime()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEvento(){
        val diag = dialogs(this)
        val dialogo = diag.showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val evento : MutableList<Eventos_Asociacion> = getEventosBusqueda(NombreEvento!!,Id!!)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (evento.size!=0){
                    if (evento[0] != null) {
                        // Update the UI with the fetched assignment details
                        txtNombre.setText(evento[0].getTitulo())
                        txtFecha.setText(evento[0].getFechaToString())
                        fecha = Date.from(evento[0].getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant())
                        txtLugar.setText(evento[0].getLugar())
                        txtDuracion.setText(evento[0].getDuracion().toString())
                        txtCategoria.setText(evento[0].getCategoria())
                        txtRequerimientos.setText(evento[0].getRequisitos())
                        txtDescripcion.setText(evento[0].getDescripcion())

                    } else {
                        // Handle the case when the assignment is not found
                        // Show an error message or take appropriate action
                        diag.showErrorDialog(99)
                    }
                } else {
                    diag.showErrorDialog(99)
                }

            }
        }
    }
}