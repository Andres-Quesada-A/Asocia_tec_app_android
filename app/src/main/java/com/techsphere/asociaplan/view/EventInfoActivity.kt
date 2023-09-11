package com.techsphere.asociaplan.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.models.Eventos

class EventInfoActivity : AppCompatActivity() {
    private lateinit var evento: Eventos
    private lateinit var titleText: TextView
    private lateinit var dateText: TextView
    private lateinit var durationText: TextView
    //private lateinit var capacityText: TextView
    private lateinit var categoryText: TextView
    private lateinit var placeText: TextView
    private lateinit var reqText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details_without_inscription)
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
        cancelButton = findViewById(R.id.button_volver)
        cancelButton.setOnClickListener {
            finish()
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
    }
}