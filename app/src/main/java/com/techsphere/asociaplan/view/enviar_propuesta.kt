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
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Propuestas_Adapter
import com.techsphere.asociaplan.controller.getAllAsociacionesBD
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class enviar_propuesta : AppCompatActivity() {
    private lateinit var editTextTematica : EditText
    private lateinit var editTextObjetivos : EditText
    private lateinit var editTextActividades : EditText
    private lateinit var editTextDetalles : EditText
    private lateinit var Enviar : Button
    private lateinit var volver : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_propuesta)
        editTextTematica = findViewById<EditText>(R.id.tematica)
        editTextObjetivos = findViewById<EditText>(R.id.objetivos)
        editTextActividades = findViewById<EditText>(R.id.actividades)
        editTextDetalles = findViewById<EditText>(R.id.detalles)
        Enviar = findViewById<Button>(R.id.button_enviar)
        volver = findViewById<Button>(R.id.button_volver)

        Enviar.setOnClickListener {
            EnviarPropuesta()
        }
        volver.setOnClickListener {
            val intent = Intent(this,asociaciones_propuesta::class.java)
            startActivity(intent)
        }
    }
    fun EnviarPropuesta(){


    }
}