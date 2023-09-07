package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Adapter
import com.techsphere.asociaplan.controller.getAllAsociacionesBD
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import com.techsphere.asociaplan.models.Asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class asociaciones : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Asociaciones_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button
    private lateinit var registerButton : Button
    private var eventosArray = arrayOf<Asociacion>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asociaciones)
        editTextNombre = findViewById<EditText>(R.id.nombre_asociacion)
        BuscarButton = findViewById<Button>(R.id.button_buscar)
        registerButton = findViewById<Button>(R.id.button_registrar)

        rv = findViewById<RecyclerView>(R.id.rvAsociacion)

        progressBar = findViewById(R.id.progBarCubiEst)
        val evento1 = Asociacion("Evento 1", "Descripción 1", "Lugar 1", "120", "a")
        val evento2 = Asociacion("Evento 2", "Descripción 2", "Lugar 2", "90", "a")
        eventosArray = arrayOf(evento1, evento2)


        cargarAsociaciones(this)
        BuscarButton.setOnClickListener {
            BuscarAsociacion(this)
        }
        registerButton.setOnClickListener {
            val intent = Intent(this,RegisterAsociationActivity::class.java)
            startActivity(intent)
        }
    }
    fun cargarAsociaciones(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val asociaciones = getAllAsociacionesBD()
            withContext(Dispatchers.Main){
                adap = Asociaciones_Adapter(eventosArray)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

    fun BuscarAsociacion(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val asociaciones = getAsociacionesBusqueda(editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Asociaciones_Adapter(eventosArray)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

}