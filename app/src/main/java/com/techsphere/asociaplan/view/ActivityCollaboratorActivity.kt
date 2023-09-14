package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Colaboradores_Adapter
import com.techsphere.asociaplan.controller.getColaboradoresEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityCollaboratorActivity : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Colaboradores_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button
    private lateinit var AddButton: Button
    private var idEvento = 0
    private var nombre = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_collaborators)
        idEvento=(intent?.extras?.getInt("id")) as Int
        nombre = (intent?.extras?.getString("nombre")) as String
        loadComponents()
        getAllCollaboratorsEvents()
    }
    fun loadComponents(){
        editTextNombre = findViewById(R.id.collaborator_name)
        BuscarButton = findViewById(R.id.button_search)
        rv = findViewById(R.id.rv_collaborator)
        progressBar = findViewById(R.id.progBarCubiEst)
        AddButton = findViewById(R.id.button_add_collaborator)
        BuscarButton.setOnClickListener {
            buscarColaborador()
        }
        AddButton.setOnClickListener {
            val intent = Intent(this, AddCollaboratorEventActivity::class.java)
            intent.putExtra("idEvento", idEvento)
            startActivity(intent)
            finish()
        }
    }
    fun getAllCollaboratorsEvents(){
        progressBar.visibility= View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val colaboradores = getColaboradoresEvents(idEvento)
            withContext(Dispatchers.Main){
                adap = Colaboradores_Adapter(colaboradores)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(this@ActivityCollaboratorActivity)
                progressBar.visibility=View.GONE
            }
        }
    }
    fun buscarColaborador(){
        progressBar.visibility= View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val colaboradores = getColaboradoresEvents(idEvento, editTextNombre.text.toString())
            withContext(Dispatchers.Main){
                adap = Colaboradores_Adapter(colaboradores)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(this@ActivityCollaboratorActivity)
                progressBar.visibility=View.GONE
            }
        }
    }
}
