package com.techsphere.asociaplan.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Button
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import kotlinx.coroutines.CoroutineScope
import com.techsphere.asociaplan.auth.AuthHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class menu_estudiante : AppCompatActivity() {
    private lateinit var HacerseColaborador: Button
    private lateinit var VerEventos: Button
    private lateinit var Inscripciones: Button
    private lateinit var EvaluarEvento: Button
    private lateinit var CrearPropuesta: Button
    private lateinit var Foro: Button
    private lateinit var CerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_estudiante)
        HacerseColaborador = findViewById<Button>(R.id.button_hacerse_colaborador)
        VerEventos = findViewById<Button>(R.id.button_ver_eventos)
        Inscripciones = findViewById<Button>(R.id.button_inscripciones)
        EvaluarEvento = findViewById<Button>(R.id.button_evaluar_evento)
        CrearPropuesta = findViewById<Button>(R.id.button_crear_propuesta)
        Foro = findViewById<Button>(R.id.button_foro)
        CerrarSesion = findViewById<Button>(R.id.button_cerrar_sesion)
        // Revisamos si cuenta con el acceso al almacenamiento
        //checkStorageAccess()
        HacerseColaborador.setOnClickListener {
            val intent = Intent(this, register_collaborator::class.java)
            startActivity(intent)
        }
        VerEventos.setOnClickListener {
            if (checkStorageAccess()){
                val intent = Intent(this, EventCalendarActivity::class.java)
                startActivity(intent)
            }
        }
        Inscripciones.setOnClickListener {
            val intent = Intent(this, InscripcionesEventosActivity::class.java)
            startActivity(intent)
        }
        EvaluarEvento.setOnClickListener {
            val intent = Intent(this,eventos_evaluar::class.java)
            startActivity(intent)
        }
        CrearPropuesta.setOnClickListener {
            val intent = Intent(this,asociaciones_propuesta::class.java)
            startActivity(intent)
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
    fun checkStorageAccess(): Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            // If you have access to the external storage, do whatever you need
            if (Environment.isExternalStorageManager()) {
                return true
            } else {
                val dialog = dialogs(this)
                dialog.showSpecialStoragePermissions()
                return false
            }
        }
        return true
    }
}