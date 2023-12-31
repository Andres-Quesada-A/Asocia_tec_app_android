package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Colaboradores_Adapter
import com.techsphere.asociaplan.controller.getColaboradoresActividad
import com.techsphere.asociaplan.controller.getColaboradoresEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivitesCollaboratorActivity : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Colaboradores_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var eventName: TextView
    private lateinit var BuscarButton : Button
    private lateinit var AddButton: Button
    private var idActividad = 0
    private var nombre = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_collaborators)
        idActividad=(intent?.extras?.getInt("id")) as Int
        nombre = (intent?.extras?.getString("nombre")) as String
        loadComponents()
        getAllCollaboratorsEvents()
    }
    fun loadComponents(){
        eventName = findViewById(R.id.titleView)
        eventName.text=nombre
        editTextNombre = findViewById(R.id.collaborator_name)
        BuscarButton = findViewById(R.id.button_search)
        rv = findViewById(R.id.rv_collaborator)
        progressBar = findViewById(R.id.progBarCubiEst)
        AddButton = findViewById(R.id.button_add_collaborator)
        BuscarButton.setOnClickListener {
            buscarColaborador()
        }
        AddButton.setOnClickListener {
            val intent = Intent(this, AddCollaboratorActivitiesActivity::class.java)
            intent.putExtra("idActividad", idActividad)
            startActivity(intent)
            finish()
        }
    }
    fun getAllCollaboratorsEvents(){
        progressBar.visibility= View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            // esto es lo que tengo que cambiar
            val colaboradores = getColaboradoresActividad(idActividad)
            withContext(Dispatchers.Main){
                adap = Colaboradores_Adapter(colaboradores, 0, idActividad,false, true)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(this@ActivitesCollaboratorActivity)
                progressBar.visibility=View.GONE
            }
        }
    }
    fun buscarColaborador(){
        progressBar.visibility= View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val colaboradores = getColaboradoresActividad(idActividad, editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Colaboradores_Adapter(colaboradores, 0, idActividad,false, true)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(this@ActivitesCollaboratorActivity)
                progressBar.visibility=View.GONE
            }
        }
    }
}
