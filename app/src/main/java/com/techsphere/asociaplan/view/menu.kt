package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.techsphere.asociaplan.R

class menu : AppCompatActivity() {
    private lateinit var IniciarSesion : Button
    private lateinit var calendarButton : Button
    private lateinit var CrearAsoc : Button
    private lateinit var Asociaciones : Button
    private lateinit var RegColab : Button
    private lateinit var Eventos : Button
    private lateinit var RegEvento : Button
    private lateinit var Foro : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        calendarButton= findViewById(R.id.button_calendario_eventos)
        IniciarSesion = findViewById<Button>(R.id.button_iniciar_sesion)
        CrearAsoc = findViewById<Button>(R.id.button_crear_asociacion)
        Asociaciones = findViewById<Button>(R.id.button_asociaciones)
        RegColab = findViewById<Button>(R.id.button_registrar_colaborador)
        Eventos = findViewById<Button>(R.id.button_eventos)
        RegEvento = findViewById<Button>(R.id.button_registrar_eventos)
        Foro = findViewById<Button>(R.id.button_Foro)
        IniciarSesion.setOnClickListener {
            val intent = Intent(this,menu::class.java)
            startActivity(intent)
        }
        CrearAsoc.setOnClickListener {
            val intent = Intent(this,RegisterAsociationActivity::class.java)
            startActivity(intent)
        }
        Asociaciones.setOnClickListener {
            val intent = Intent(this,asociaciones::class.java)
            startActivity(intent)
        }
        RegColab.setOnClickListener {
            //val intent = Intent(this,register_collaborator::class.java)
            //startActivity(intent)
        }
        Eventos.setOnClickListener {
            //val intent = Intent(this,events::class.java)
            //startActivity(intent)
        }
        RegEvento.setOnClickListener {
            //val intent = Intent(this,register_event::class.java)
            //startActivity(intent)
        }
        Foro.setOnClickListener {
            //val intent = Intent(this,menu::class.java)
            //startActivity(intent)
        }
        calendarButton.setOnClickListener {
            startActivity(Intent(this, EventCalendarActivity::class.java))
        }
    }
}