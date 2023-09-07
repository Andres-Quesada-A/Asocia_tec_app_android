package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.techsphere.asociaplan.R

class menu_admin : AppCompatActivity() {
    private lateinit var asociaciones : Button
    private lateinit var colaboradores : Button
    private lateinit var eventos : Button
    private lateinit var calendario : Button
    private lateinit var foro : Button
    private lateinit var informes : Button
    private lateinit var evaluaciones : Button
    private lateinit var analisis_datos : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)
        asociaciones = findViewById(R.id.button_asociaciones)
        colaboradores = findViewById(R.id.button_collaborator)
        eventos = findViewById(R.id.button_eventos)
        calendario = findViewById(R.id.button_calendario)
        foro = findViewById(R.id.button_foro)
        informes = findViewById(R.id.button_informes)
        evaluaciones = findViewById(R.id.button_evaluaciones)
        analisis_datos = findViewById(R.id.button_analisis_data)

        asociaciones.setOnClickListener {
            val intent = Intent(this, asociaciones::class.java)
            startActivity(intent)
        }
        colaboradores.setOnClickListener {
            val intent = Intent(this, collaborator_list::class.java)
            startActivity(intent)
        }
        eventos.setOnClickListener {
            val intent = Intent(this, events::class.java)
            startActivity(intent)
        }
        calendario.setOnClickListener {
            val intent = Intent(this, EventCalendarActivity::class.java)
            startActivity(intent)
        }
        foro.setOnClickListener {
            //val intent = Intent(this, EventCalendarActivity::class.java)
            //startActivity(intent)
        }
        informes.setOnClickListener {
            //val intent = Intent(this, EventCalendarActivity::class.java)
            //startActivity(intent)
        }
        evaluaciones.setOnClickListener {
            //val intent = Intent(this, EventCalendarActivity::class.java)
            //startActivity(intent)
        }
        analisis_datos.setOnClickListener {
            //val intent = Intent(this, EventCalendarActivity::class.java)
            //startActivity(intent)
        }


    }
}