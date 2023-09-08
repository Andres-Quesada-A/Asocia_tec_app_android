package com.techsphere.asociaplan.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techsphere.asociaplan.R

class menu_asociacion : AppCompatActivity() {
    private lateinit var miembros : Button
    private lateinit var eventos : Button
    private lateinit var actividades : Button
    private lateinit var capacidad : Button
    private lateinit var colaboradores : Button
    private lateinit var informes : Button
    private lateinit var estadisticas : Button
    private lateinit var Foro : Button
    private lateinit var CerrarSesion : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_asociacion)
        miembros = findViewById<Button>(R.id.button_miembros)
        eventos = findViewById<Button>(R.id.button_eventos)
        actividades = findViewById<Button>(R.id.button_actividades)
        capacidad = findViewById<Button>(R.id.button_capacidad)
        colaboradores = findViewById<Button>(R.id.button_gestion_colaborador)
        informes = findViewById<Button>(R.id.button_informes)
        estadisticas = findViewById<Button>(R.id.button_estadisticas)
        Foro = findViewById<Button>(R.id.button_foro)
        CerrarSesion = findViewById<Button>(R.id.button_cerrar_sesion)
        miembros.setOnClickListener {
            val intent = Intent(this,register_collaborator::class.java)
            startActivity(intent)
        }
        eventos.setOnClickListener {
            val intent = Intent(this,EventCalendarActivity::class.java)
            startActivity(intent)
        }
        actividades.setOnClickListener {

        }
        capacidad.setOnClickListener {

        }
        colaboradores.setOnClickListener {

        }
        informes.setOnClickListener {

        }
        estadisticas.setOnClickListener {

        }
        Foro.setOnClickListener {

        }
        CerrarSesion.setOnClickListener {

        }
    }
}