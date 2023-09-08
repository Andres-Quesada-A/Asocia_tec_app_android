package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Adapter
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Propuestas_Adapter
import com.techsphere.asociaplan.controller.getAllAsociacionesBD
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class asociaciones_propuesta : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Asociaciones_Propuestas_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button
    private lateinit var volver : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asociaciones_propuesta)
        editTextNombre = findViewById<EditText>(R.id.nombre_asociacion)
        BuscarButton = findViewById<Button>(R.id.button_buscar)
        volver = findViewById<Button>(R.id.button_volver)

        rv = findViewById<RecyclerView>(R.id.rvAsociacionPropuesta)

        progressBar = findViewById(R.id.progBarCubiEst)

        cargarAsociacionesPropuesta(this)
        BuscarButton.setOnClickListener {
            BuscarAsociacionPropuesta(this)
        }
        volver.setOnClickListener {
            val intent = Intent(this,menu_estudiante::class.java)
            startActivity(intent)
        }
    }
    fun cargarAsociacionesPropuesta(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val asociaciones = getAllAsociacionesBD()
            withContext(Dispatchers.Main){
                adap = Asociaciones_Propuestas_Adapter(asociaciones)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

    fun BuscarAsociacionPropuesta(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val asociaciones = getAsociacionesBusqueda(editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Asociaciones_Propuestas_Adapter(asociaciones)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
}