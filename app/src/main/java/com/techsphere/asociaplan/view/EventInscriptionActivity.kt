package com.techsphere.asociaplan.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.Administrador
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.utils.ConfirmationEmail
import com.techsphere.asociaplan.utils.EmailSender

import com.techsphere.asociaplan.utils.generate_qr_code
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Duration

class EventInscriptionActivity : AppCompatActivity() {
    private lateinit var evento: Eventos
    private lateinit var titleText: TextView
    private lateinit var dateText: TextView
    private lateinit var durationText: TextView
    private lateinit var capacityText: TextView
    private lateinit var categoryText: TextView
    private lateinit var placeText: TextView
    private lateinit var reqText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var cancelButton: Button
    private lateinit var inscribeButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details_with_inscription)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            evento = intent.getSerializableExtra("Evento", Eventos::class.java)!!
        } else evento = (intent.getSerializableExtra("Evento") as Eventos)
        getButtonsAndTextViews()
        setEventInfo()

    }
    private fun getButtonsAndTextViews(){
        titleText = findViewById(R.id.title)
        dateText = findViewById(R.id.date)
        durationText = findViewById(R.id.duration)
        categoryText = findViewById(R.id.category)
        placeText = findViewById(R.id.place)
        reqText = findViewById(R.id.requirements)
        descriptionText = findViewById(R.id.description)
        cancelButton= findViewById(R.id.button_cancel_inscription)
        inscribeButton= findViewById(R.id.button_subscription)
        capacityText= findViewById(R.id.capacity)
        cancelButton.setOnClickListener {
            finish()
        }
        inscribeButton.setOnClickListener {
            registerUserInActivity()
        }
    }
    private fun setEventInfo(){
        titleText.text="Nombre: ${evento.getTitulo()}"
        dateText.text="Fecha: ${evento.getFecha()}"
        durationText.text="Duracion: ${evento.getDuracion()}"
        categoryText.text="Categoria: ${evento.getCategoria()}"
        placeText.text="Lugar del evento: ${evento.getLugar()}"
        reqText.text="Requisitos especiales: ${evento.getRequisitos()}"
        descriptionText.text="Descripcion: ${evento.getDescripcion()}"
        capacityText.text="Cupos Limitados"
    }
    private fun registerUserInActivity(){
        val dialogs= dialogs(this)
        val dialogCarga = dialogs.showLoadingDialog()
        val authHelper: AuthHelper= AuthHelper(this)
        val admin: Administrador = Administrador()
        val userId = authHelper.getAccountId()
        val eventId = evento.getId()
        val emailSender = ConfirmationEmail()
        val emailSenderSimple = EmailSender()
        val emailAuth = authHelper.getAccountEmail()
        val contentEmail = "Ha sido inscripto en el evento ${evento.getTitulo()}, que se realizará el ${evento.getFecha()} en ${evento.getLugar()}"
        val ContentQR = "Evento: ${evento.getTitulo()}, identificadorEvento: ${evento.getId()}, idUser: ${authHelper.getAccountId()}, email: ${authHelper.getAccountEmail()}"
        
        dialogCarga.show()
        CoroutineScope(Dispatchers.IO).launch {
            val emailAsociacion = admin.getAsociationEmail(eventId)
            val fields = admin.ConsultarCapacidadEvento(eventId)
            val resultSp = admin.inscribirEstudianteEvento(eventId,userId)
            dialogCarga.dismiss()
            withContext(Dispatchers.Main) {
                if (resultSp == 1) {
                    if (emailAuth != null){
                        val QRImage = generate_qr_code(ContentQR)
                        emailSender.sendEmail(emailAuth, "Notificación de inscripción", contentEmail, QRImage)
                    }
                } else if(resultSp==50002){
                    dialogs.showErrorDialog(16)
                    if (fields == 0 && emailAsociacion != null){
                        emailSenderSimple.sendEmail("emailAsociacion", "Capacidad del evento completa", "El evento ${evento.getTitulo()} ha completado su capacidad de colaboradores")
                    }
                }else{
                    dialogs.showErrorDialog(17)
                }
            }
        }
    }
}