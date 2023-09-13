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
import com.techsphere.asociaplan.UI.adapters.Eventos_Adapter
import com.techsphere.asociaplan.UI.adapters.Eventos_Informe_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getAllEventosBD
import com.techsphere.asociaplan.controller.getEventosBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class eventos_informe : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Eventos_Informe_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextTitulo : EditText
    private lateinit var BuscarButton : Button
    private var Id : Int? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos_informe)

        Id = AuthHelper(this).getAccountId()
        editTextTitulo = findViewById<EditText>(R.id.title)
        BuscarButton = findViewById<Button>(R.id.button_search)

        rv = findViewById<RecyclerView>(R.id.rv_eventos_informe)

        progressBar = findViewById(R.id.progBarCubiEst)

        cargarEventos(this)
        BuscarButton.setOnClickListener {
            BuscarEventos(this)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarEventos(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = getAllEventosBD(Id!!)
            withContext(Dispatchers.Main){
                adap = Eventos_Informe_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun BuscarEventos(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val eventos = getEventosBusqueda(editTextTitulo.text.toString(),Id!!)
            withContext(Dispatchers.Main){
                adap = Eventos_Informe_Adapter(eventos)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
}