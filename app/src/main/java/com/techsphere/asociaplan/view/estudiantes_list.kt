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
    private lateinit var RegistrarButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes_list)
        editTextNombre = findViewById<EditText>(R.id.nombre_estudiante)
        BuscarButton = findViewById<Button>(R.id.button_buscar)
        RegistrarButton = findViewById<Button>(R.id.button_registrar)

        rv = findViewById<RecyclerView>(R.id.rvEstudiantes)

        progressBar = findViewById(R.id.progBarCubiEst)


        cargarEstudiantes(this)
        BuscarButton.setOnClickListener {
            BuscarEstuadiantes(this)
        }

        RegistrarButton.setOnClickListener {
            signUpUser()
        }
    }
    fun signUpUser(){
        // No se finaliza la actividad actual para que cuando se termine de registrar
        // el usuario vuelva rapidamente al login
        startActivity(Intent(this, RegisterActivity_Admin::class.java))
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