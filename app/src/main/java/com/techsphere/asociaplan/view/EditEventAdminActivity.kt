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

class EditEventAdminActivity : AppCompatActivity() {
    private lateinit var evento: Eventos_Asociacion
    private lateinit var txtNombre : EditText
    private lateinit var txtFecha : EditText
    private lateinit var txtLugar : EditText
    private lateinit var txtDuracion : EditText
    private lateinit var txtCategoria : EditText
    private lateinit var txtRequerimientos : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnUpdate : Button
    private var fecha: java.util.Date? =null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            evento = intent.getSerializableExtra("Evento", Eventos_Asociacion::class.java)!!
        } else evento = (intent.getSerializableExtra("Evento") as Eventos_Asociacion)
        getButtonsAndText()
        setEventInfo()

        //cargarEvento()
    }

    fun getButtonsAndText(){
        txtNombre = findViewById(R.id.event_name)
        txtFecha = findViewById(R.id.date)
        txtLugar = findViewById(R.id.place)
        txtDuracion = findViewById(R.id.duration)
        txtCategoria = findViewById(R.id.category)
        txtRequerimientos = findViewById(R.id.requirements)
        txtDescripcion = findViewById(R.id.description)
        btnUpdate = findViewById(R.id.button_update)
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
            val res = EditarEventoInBD(0,evento.getId(),Nombre,java.sql.Date((fecha as java.util.Date).time),Descripcion,Lugar,Duracion.toInt(),Requerimientos,Categoria)
            if (res == 1){
                // Le pedimos a la BD que nos devuelvan los correos
                val correos = Administrador().getInteresadosEvento(evento.getId())
                // Revisamos si obtuvimos al menos un correo
                if (correos.size!=0){
                    withContext(Dispatchers.Main){
                        load.dismiss()
                    }
                    //Falta obtener los correos
                    emailSender.sendEmail(correos[0], "Actualización del evento", contentEmail)
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
    fun setEventInfo(){
        txtNombre.setText(evento.getTitulo())
        txtFecha.setText(evento.getFechaToString())

        fecha=Date.from(evento.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant())
        txtLugar.setText(evento.getLugar())
        txtDuracion.setText("${evento.getDuracion()}")
        txtCategoria.setText(evento.getCategoria())
        txtRequerimientos.setText(evento.getRequisitos())
        txtDescripcion.setText(evento.getDescripcion())
    }
}
