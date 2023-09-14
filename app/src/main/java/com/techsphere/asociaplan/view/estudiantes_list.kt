package com.techsphere.asociaplan.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Estudiantes_Adapter
import com.techsphere.asociaplan.controller.getAllEstudiantesBD
import com.techsphere.asociaplan.controller.getEstudiantesBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class estudiantes_list : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Estudiantes_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes_list)
        editTextNombre = findViewById<EditText>(R.id.nombre_estudiante)
        BuscarButton = findViewById<Button>(R.id.button_buscar)

        rv = findViewById<RecyclerView>(R.id.rvEstudiantes)

        progressBar = findViewById(R.id.progBarCubiEst)


        cargarEstudiantes(this)
        BuscarButton.setOnClickListener {
            BuscarEstuadiantes(this)
        }
    }
    fun cargarEstudiantes(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val estudiantes = getAllEstudiantesBD()
            withContext(Dispatchers.Main){
                adap = Estudiantes_Adapter(estudiantes)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
                }
            }
        }

        fun BuscarEstuadiantes(view: Context){
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch{
                val estudiantes = getEstudiantesBusqueda(editTextNombre.text.toString())
                withContext(Dispatchers.Main){
                    adap = Estudiantes_Adapter(estudiantes)
                    rv.adapter=adap
                    rv.layoutManager = LinearLayoutManager(view)
                    progressBar.visibility= View.GONE
                }
            }
        }

    }