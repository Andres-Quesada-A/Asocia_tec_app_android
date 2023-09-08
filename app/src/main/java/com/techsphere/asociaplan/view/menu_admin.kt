package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.techsphere.asociaplan.R

class menu_admin : AppCompatActivity() {
    private lateinit var asociaciones : Button
    private lateinit var colaboradores : Button
    private lateinit var estudiantes : Button
    private lateinit var cerrarSesion : Button
    private lateinit var foro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)
        asociaciones = findViewById(R.id.button_asociaciones)
        colaboradores = findViewById(R.id.button_colaboradores)
        estudiantes = findViewById(R.id.button_estudiantes)
        foro = findViewById(R.id.button_foro)
        cerrarSesion = findViewById(R.id.button_cerrar_sesion)

        asociaciones.setOnClickListener {
            val intent = Intent(this, asociaciones::class.java)
            startActivity(intent)
        }
        colaboradores.setOnClickListener {
            val intent = Intent(this, collaborator_list::class.java)
            startActivity(intent)
        }
        estudiantes.setOnClickListener {
            val intent = Intent(this, events::class.java)
            startActivity(intent)
        }
        foro.setOnClickListener {
            //val intent = Intent(this, EventCalendarActivity::class.java)
            //startActivity(intent)
        }
        cerrarSesion.setOnClickListener {
            //val intent = Intent(this, EventCalendarActivity::class.java)
            //startActivity(intent)
        }
    }
}