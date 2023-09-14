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
import com.techsphere.asociaplan.UI.adapters.Actividades_Adapter
import com.techsphere.asociaplan.UI.adapters.Eventos_Actividad_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getActividadesBusqueda
import com.techsphere.asociaplan.controller.getAllActividadesBD
import com.techsphere.asociaplan.controller.getAllEventosBD
import com.techsphere.asociaplan.controller.getEventosBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class actividades : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Actividades_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextTitulo : EditText
    private lateinit var BuscarButton : Button
    private lateinit var RegistrarButton : Button
    private var Id : Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividades)

        Id = (intent?.extras?.getInt("id")) as Int
        editTextTitulo = findViewById<EditText>(R.id.title)
        BuscarButton = findViewById<Button>(R.id.button_search)
        RegistrarButton = findViewById<Button>(R.id.button_register)

        rv = findViewById<RecyclerView>(R.id.rv_actividades)

        progressBar = findViewById(R.id.progBarCubiEst)

        cargarActividades(this)
        BuscarButton.setOnClickListener {
            buscarActividades(this)
        }
        RegistrarButton.setOnClickListener(){
            intent = Intent(this, registrar_actividad::class.java)
            intent.putExtra("id",Id)
            this.startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarActividades(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val actividades = getAllActividadesBD(Id)
            withContext(Dispatchers.Main){
                adap = Actividades_Adapter(actividades, Id)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarActividades(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val actividades = getActividadesBusqueda(editTextTitulo.text.toString(),Id)
            withContext(Dispatchers.Main){
                adap = Actividades_Adapter(actividades, Id)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
}