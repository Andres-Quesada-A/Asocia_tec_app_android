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
import com.techsphere.asociaplan.UI.adapters.Eventos_Evaluar_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getAllAsociacionesBD
import com.techsphere.asociaplan.controller.getAllAventosEvaluarBD
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import com.techsphere.asociaplan.controller.getEventosEvaluarBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class eventos_evaluar : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Eventos_Evaluar_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button
    private lateinit var volver : Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_evaluar)
        editTextNombre = findViewById<EditText>(R.id.nombre_evento)
        BuscarButton = findViewById<Button>(R.id.button_buscar)
        volver = findViewById<Button>(R.id.button_volver)

        rv = findViewById<RecyclerView>(R.id.rvEventoEvaluar)
        val authHelper: AuthHelper = AuthHelper(this)
        val idUsuario = authHelper.getAccountId()
        progressBar = findViewById(R.id.progBarCubiEst)
        cargarEventosEvaluar(this,idUsuario)
        BuscarButton.setOnClickListener {
            BuscarEventosEvaluar(this, idUsuario)
        }
        volver.setOnClickListener {
            val intent = Intent(this,menu_estudiante::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEventosEvaluar(view: Context, idUsuario: Int){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = getAllAventosEvaluarBD(idUsuario)
            withContext(Dispatchers.Main){
                adap = Eventos_Evaluar_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun BuscarEventosEvaluar(view: Context, idUsuario: Int){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = getEventosEvaluarBusqueda(idUsuario, editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Eventos_Evaluar_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
}