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
import com.techsphere.asociaplan.controller.getAllEstudiantesBD
import com.techsphere.asociaplan.controller.getEstudiantesBusqueda
import com.techsphere.asociaplan.models.Estudiantes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class agregar_miembros : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Agregar_Miembros_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private var eventosArray = arrayOf<Estudiantes>()
    var correo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_miembros)

        correo = (intent?.extras?.getString("correo")) as String
        editTextNombre = findViewById<EditText>(R.id.nombre_asociacion)
        val BuscarButton = findViewById<Button>(R.id.button_buscar)

        rv = findViewById<RecyclerView>(R.id.rvMiembros)

        progressBar = findViewById(R.id.progBarCubiEst)

        val evento1 = Estudiantes("Evento 1", "Descripción 1", "Lugar 1")
        val evento2 = Estudiantes("Evento 2", "Descripción 2", "Lugar 2")
        eventosArray = arrayOf(evento1, evento2)

        cargarEstudiantes(this)
        BuscarButton.setOnClickListener {
            BuscarEstudiante(this)
        }
    }
    private fun cargarEstudiantes(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val Estudiantes = getAllEstudiantesBD(correo)
            withContext(Dispatchers.Main){
                adap = Agregar_Miembros_Adapter(eventosArray)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

    fun BuscarEstudiante(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val Estudiantes = getEstudiantesBusqueda(correo, editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Agregar_Miembros_Adapter(eventosArray)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

}