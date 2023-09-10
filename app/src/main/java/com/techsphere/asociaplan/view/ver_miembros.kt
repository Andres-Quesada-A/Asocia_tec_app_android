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
import com.techsphere.asociaplan.UI.adapters.Agregar_Miembros_Adapter
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Adapter
import com.techsphere.asociaplan.controller.getAllEstudiantesMiembrosBD
import com.techsphere.asociaplan.controller.getEstudiantesMiembrosBusqueda
import com.techsphere.asociaplan.models.Estudiantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ver_miembros : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Agregar_Miembros_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_miembros)

        id = (intent?.extras?.getInt("id")) as Int
        editTextNombre = findViewById<EditText>(R.id.nombre_asociacion)
        val BuscarButton = findViewById<Button>(R.id.button_buscar)

        rv = findViewById<RecyclerView>(R.id.rvMiembros)

        progressBar = findViewById(R.id.progBarCubiEst)

        cargarEstudiantes(this)
        BuscarButton.setOnClickListener {
            BuscarEstudiante(this)
        }
    }
    private fun cargarEstudiantes(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val Estudiantes = getAllEstudiantesMiembrosBD(id)
            withContext(Dispatchers.Main){
                adap = Agregar_Miembros_Adapter(Estudiantes)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

    fun BuscarEstudiante(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val Estudiantes = getEstudiantesMiembrosBusqueda(id, editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Agregar_Miembros_Adapter(Estudiantes)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

}