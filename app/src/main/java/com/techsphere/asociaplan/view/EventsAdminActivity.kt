package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Eventos_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getAllEventsAdmin
import com.techsphere.asociaplan.controller.getEventosBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsAdminActivity : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Eventos_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextTitulo : EditText
    private lateinit var BuscarButton : Button
    private lateinit var registerButton : Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        editTextTitulo = findViewById(R.id.title)
        BuscarButton = findViewById(R.id.button_search)
        registerButton = findViewById(R.id.button_register)
        rv = findViewById(R.id.rv_events)
        progressBar = findViewById(R.id.progBarCubiEst)
        BuscarButton.setOnClickListener {
            buscarEventos()
        }
        registerButton.setOnClickListener {
            val intent = Intent(this,register_event::class.java)
            startActivity(intent)
        }
        cargarEventos(this)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEventos(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = getAllEventsAdmin()
            withContext(Dispatchers.Main){
                adap = Eventos_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarEventos(){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = getAllEventsAdmin()
            withContext(Dispatchers.Main){
                adap = Eventos_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(this@EventsAdminActivity)
                progressBar.visibility= View.GONE
            }
        }
    }
}