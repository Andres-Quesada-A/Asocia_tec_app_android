package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class menu_asociacion : AppCompatActivity() {
    private lateinit var verMiembros : Button
    private lateinit var agregarMiembros : Button
    private lateinit var eventos : Button
    private lateinit var actividades : Button
    private lateinit var capacidad : Button
    private lateinit var colaboradores : Button
    private lateinit var propuestas : Button
    private lateinit var informes : Button
    private lateinit var estadisticas : Button
    private lateinit var Foro : Button
    private lateinit var CerrarSesion : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_asociacion)
        verMiembros = findViewById<Button>(R.id.button_ver_miembros)
        agregarMiembros = findViewById<Button>(R.id.button_agregar_miembros)
        eventos = findViewById<Button>(R.id.button_eventos)
        actividades = findViewById<Button>(R.id.button_actividades)
        capacidad = findViewById<Button>(R.id.button_capacidad)
        colaboradores = findViewById<Button>(R.id.button_gestion_colaborador)
        propuestas = findViewById<Button>(R.id.button_gestion_propuestas)
        informes = findViewById<Button>(R.id.button_informes)
        estadisticas = findViewById<Button>(R.id.button_estadisticas)
        Foro = findViewById<Button>(R.id.button_foro)
        CerrarSesion = findViewById<Button>(R.id.button_cerrar_sesion)
        verMiembros.setOnClickListener {
            val intent = Intent(this,ver_miembros::class.java)
            intent.putExtra("id", AuthHelper(this).getAccountId().toInt())
            this.startActivity(intent)
        }
        agregarMiembros.setOnClickListener {
            val intent = Intent(this,agregar_miembros::class.java)
            intent.putExtra("id", AuthHelper(this).getAccountId().toInt())
            this.startActivity(intent)
        }
        eventos.setOnClickListener {
            val intent = Intent(this,events::class.java)
            this.startActivity(intent)
        }
        actividades.setOnClickListener {

        }
        capacidad.setOnClickListener {

        }
        colaboradores.setOnClickListener {

        }
        propuestas.setOnClickListener {
            val intent = Intent(this,gestion_propuestas::class.java)
            startActivity(intent)
        }
        informes.setOnClickListener {

        }
        estadisticas.setOnClickListener {

        }
        Foro.setOnClickListener {
            val intent = Intent(this, forum_main_view::class.java)
            startActivity(intent)
        }
        CerrarSesion.setOnClickListener {
            logout(this)
        }
    }
    fun logout(view: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val res = AuthHelper(view).logoutAccount()
            if (res) {
                startActivity(Intent(view, LoginActivity::class.java))
                finish()
            }
        }
    }
}