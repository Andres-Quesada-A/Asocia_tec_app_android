package com.techsphere.asociaplan.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Eventos_Calendario_Adapter
import com.techsphere.asociaplan.UI.adapters.Eventos_Inscripcion_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.Administrador
import com.techsphere.asociaplan.controller.EventosController
import com.techsphere.asociaplan.models.Eventos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.time.LocalDate

class InscripcionesEventosActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: Eventos_Inscripcion_Adapter
    private lateinit var progressBar: ProgressBar
    private lateinit var eventosArray: MutableList<Eventos>
    private lateinit var searchButton: Button
    private lateinit var editTextNombre : EditText
    private var eventosCtrl = EventosController()
    private var admin = Administrador()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscripciones_eventos)
        editTextNombre = findViewById(R.id.nombre_evento)
        searchButton=findViewById(R.id.button_buscar)
        rv = findViewById(R.id.rvEventoInscritos)
        progressBar = findViewById(R.id.progBar)
        progressBar.visibility= View.INVISIBLE
        searchButton.setOnClickListener {
            mostrarEventosInscritos()
        }
        mostrarEventosInscritos()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mostrarEventosInscritos(){
        val authHelper = AuthHelper(this)
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
            //Hay que añadir un texto que aparezca cuando no hayan eventos
            withContext(Dispatchers.IO){
                val idUser= authHelper.getAccountId()
                eventosArray=admin.getInscripcionesEstudiante(idUser,editTextNombre.text.toString())
            }
            adapter= Eventos_Inscripcion_Adapter(eventosArray)
            rv.adapter=adapter
            rv.layoutManager= LinearLayoutManager(this@InscripcionesEventosActivity)
            progressBar.visibility = View.INVISIBLE
        }
    }
}