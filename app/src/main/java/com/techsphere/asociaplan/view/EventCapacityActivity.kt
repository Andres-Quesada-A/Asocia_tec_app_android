package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Propuestas_Adapter
import com.techsphere.asociaplan.UI.adapters.Eventos_Capacidad_Adapter
import com.techsphere.asociaplan.UI.adapters.Eventos_Evaluar_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventCapacityActivity : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Eventos_Capacidad_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button
    private var admin = Administrador()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_capacidad_evento)
        editTextNombre = findViewById(R.id.nombre_evento)
        BuscarButton = findViewById(R.id.button_buscar)
        //volver = findViewById<Button>(R.id.button_volver)
        rv = findViewById(R.id.rvEventoEvaluar)
        val authHelper: AuthHelper = AuthHelper(this)
        val idUsuario = authHelper.getAccountId()
        progressBar = findViewById(R.id.progBarCubiEst)
        cargarEventosEvaluar()
        BuscarButton.setOnClickListener {
            //BuscarEventosEvaluar(this)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEventosEvaluar(){
        val userId = AuthHelper(this).getAccountId()
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = admin.getAllEventosAsociation(userId)
            withContext(Dispatchers.Main){
                adap = Eventos_Capacidad_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(this@EventCapacityActivity)
                progressBar.visibility= View.INVISIBLE
            }
        }
    }

}