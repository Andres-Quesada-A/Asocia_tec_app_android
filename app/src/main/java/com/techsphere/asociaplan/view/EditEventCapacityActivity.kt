package com.techsphere.asociaplan.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.models.Eventos
import java.util.regex.Pattern

class EditEventCapacityActivity : AppCompatActivity() {
    private lateinit var titleText: EditText
    private lateinit var dateText: EditText
    private lateinit var placeText: EditText
    private lateinit var durationText: EditText
    private lateinit var categoryText: EditText
    private lateinit var capacityText: EditText
    private lateinit var reqText: EditText
    private lateinit var descText: EditText
    private lateinit var updateButton: Button
    private lateinit var evento: Eventos

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            evento = intent.getSerializableExtra("Evento", Eventos::class.java)!!
        } else evento = (intent.getSerializableExtra("Evento") as Eventos)
        initElements()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initElements(){
        titleText=findViewById(R.id.event_name)
        dateText=findViewById(R.id.date)
        placeText=findViewById(R.id.place)
        durationText=findViewById(R.id.duration)
        categoryText=findViewById(R.id.category)
        capacityText=findViewById(R.id.capacity)
        reqText=findViewById(R.id.requirements)
        descText=findViewById(R.id.description)
        findViewById<TextView>(R.id.titleView).text="Gestionar Capacidad Evento"
        updateButton=findViewById(R.id.button_update)
        updateButton.text="Actualizar capacidad"
        titleText.isEnabled=false
        titleText.setText(evento.getTitulo())
        dateText.isEnabled=false
        dateText.setText(evento.getFechaToString())
        placeText.isEnabled=false
        placeText.setText(evento.getLugar())
        durationText.isEnabled=false
        durationText.setText(evento.getDuracion().toString())
        categoryText.isEnabled=false
        categoryText.setText(evento.getCategoria())
        reqText.isEnabled=false
        reqText.setText(evento.getRequisitos())
        descText.isEnabled=false
        descText.setText(evento.getDescripcion())
        updateButton.setOnClickListener {
            actualizarCapacidad()
        }
    }
    private fun actualizarCapacidad(){
        val userId = AuthHelper(this).getAccountId()
        val eventoId= evento.getId()
        var isCapacityEmpty = false
        var isCapacityANumber = true
        val nuevaCapacidad = capacityText.text.toString()
        if (nuevaCapacidad.isBlank()||nuevaCapacidad.isEmpty()){
            isCapacityEmpty=true
        } else {
            if (!Pattern.matches("[0-9]+",
                    nuevaCapacidad)){
                capacityText.error="Ingrese un numero valido"
            }
        }
        if (isCapacityANumber && !isCapacityEmpty){

        } else{
            if (isCapacityEmpty){
                capacityText.error="Por favor indique una capacidad valida"
            }
        }
    }
}