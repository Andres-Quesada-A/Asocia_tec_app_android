package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class menu_admin : AppCompatActivity() {
    private lateinit var Asociaciones : Button
    private lateinit var colaboradores : Button
    private lateinit var estudiantes : Button
    private lateinit var cerrarSesion : Button
    private lateinit var foro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)
        Asociaciones = findViewById(R.id.button_asociaciones)
        colaboradores = findViewById(R.id.button_colaboradores)
        estudiantes = findViewById(R.id.button_estudiantes)
        foro = findViewById(R.id.button_foro)
        cerrarSesion = findViewById(R.id.button_cerrar_sesion)
        Asociaciones.setOnClickListener {
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