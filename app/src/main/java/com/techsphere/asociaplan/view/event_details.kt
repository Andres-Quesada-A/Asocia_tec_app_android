package com.techsphere.asociaplan.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.textview.MaterialTextView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.UI.fragments.DatePickerFragment
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.EditarEventoInBD
import com.techsphere.asociaplan.controller.getEventosBusqueda
import com.techsphere.asociaplan.models.Eventos_Asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.time.ZoneId
import java.util.*

class event_details : AppCompatActivity() {
    private lateinit var txtNombre : MaterialTextView
    private lateinit var txtFecha : MaterialTextView
    private lateinit var txtLugar : MaterialTextView
    private lateinit var txtDuracion : MaterialTextView
    private lateinit var txtCategoria : MaterialTextView
    private lateinit var txtRequerimientos : MaterialTextView
    private lateinit var txtDescripcion : MaterialTextView
    private lateinit var btnBack : Button
    private lateinit var btnShare : Button
    private lateinit var eventoAso: Eventos_Asociacion

    private var Id : Int? = null
    private var IdEvento : Int? = null
    private var NombreEvento : String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        txtNombre = findViewById(R.id.title)
        txtNombre.setEnabled(false);
        txtFecha = findViewById(R.id.date)
        txtFecha.setEnabled(false);
        txtLugar = findViewById(R.id.place)
        txtLugar.setEnabled(false);
        txtDuracion = findViewById(R.id.duration)
        txtDuracion.setEnabled(false);
        txtCategoria = findViewById(R.id.category)
        txtCategoria.setEnabled(false);
        txtRequerimientos = findViewById(R.id.requirements)
        txtRequerimientos.setEnabled(false);
        txtDescripcion = findViewById(R.id.description)
        txtDescripcion.setEnabled(false);
        btnBack = findViewById(R.id.button_back)
        btnShare = findViewById(R.id.button_share)

        Id = AuthHelper(this).getAccountId()
        if (AuthHelper(this).getAccountType()==3){
            IdEvento = (intent?.extras?.getInt("id")) as Int
            NombreEvento = (intent?.extras?.getString("nombre")) as String
            cargarEvento()
        } else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                eventoAso = intent.getSerializableExtra("Evento", Eventos_Asociacion::class.java)!!
            } else eventoAso = (intent.getSerializableExtra("Evento") as Eventos_Asociacion)
            cargarEventoAdmin()
        }



        btnBack.setOnClickListener {
            val intent = Intent(this,events::class.java)
            this.startActivity(intent)
            (this as Activity).finish()
        }
        btnShare.setOnClickListener {
            shareEvent()
        }
    }

    fun cargarEventoAdmin(){
        txtNombre.setText("Titulo: ${eventoAso.getTitulo()}")
        txtFecha.setText("Fecha: ${eventoAso.getFecha().toString()}")
        txtLugar.setText("Lugar del evento: ${eventoAso.getLugar()}")
        txtDuracion.setText("Duración: ${eventoAso.getDuracion().toString()}")
        txtCategoria.setText("Categoría: ${eventoAso.getCategoria()}")
        txtRequerimientos.setText("Requerimientos: ${eventoAso.getRequisitos()}")
        txtDescripcion.setText("Descripción: ${eventoAso.getDescripcion()}")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEvento(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val evento : MutableList<Eventos_Asociacion> = getEventosBusqueda(NombreEvento!!,Id!!)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (evento[0] != null) {
                    // Update the UI with the fetched assignment details
                    eventoAso=evento[0]
                    txtNombre.setText("Titulo: ${evento[0].getTitulo()}")
                    txtFecha.setText("Fecha: ${evento[0].getFecha().toString()}")
                    txtLugar.setText("Lugar del evento: ${evento[0].getLugar()}")
                    txtDuracion.setText("Duración: ${evento[0].getDuracion().toString()}")
                    txtCategoria.setText("Categoría: ${evento[0].getCategoria()}")
                    txtRequerimientos.setText("Requerimientos: ${evento[0].getRequisitos()}")
                    txtDescripcion.setText("Descripción: ${evento[0].getDescripcion()}")

                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun shareEvent(){
        val eventoString = """
        ¡No te pierdas nuestro próximo evento!
    
        Título: ${eventoAso.getTitulo()}
        Descripción: ${eventoAso.getDescripcion()}
        Fecha: ${eventoAso.getFecha()}
        Lugar: ${eventoAso.getLugar()}
        Duración: ${eventoAso.getDuracion()} horas
        Requisitos: ${eventoAso.getRequisitos()}
        
        Este evento es de cupo limitado, ¡asegura tu lugar!
        
        ¡Te esperamos!
        """.trimIndent()

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, eventoString)
            putExtra(Intent.EXTRA_TITLE, "Compartir Evento")
            putExtra(Intent.EXTRA_SUBJECT, "Compartir Evento")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
}