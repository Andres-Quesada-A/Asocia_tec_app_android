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
import com.techsphere.asociaplan.UI.adapters.Colaboradores_Adapter
import com.techsphere.asociaplan.controller.getColaboradoresBusqueda
import com.techsphere.asociaplan.controller.getAllColaboradoresBD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class collaborator_list : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Colaboradores_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var editTextNombre : EditText
    private lateinit var BuscarButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collaborator_list)
        editTextNombre = findViewById<EditText>(R.id.nombre_colaborador)
        BuscarButton = findViewById<Button>(R.id.button_buscar)

        rv = findViewById<RecyclerView>(R.id.rvColaboradores)

        progressBar = findViewById(R.id.progBarCubiEst)


        cargarColaboradores(this)
        BuscarButton.setOnClickListener {
            BuscarColaborador(this)
        }
    }
    fun cargarColaboradores(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val colaboradores = getAllColaboradoresBD()
            withContext(Dispatchers.Main){
                adap = Colaboradores_Adapter(colaboradores)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
                }
            }
        }

        fun BuscarColaborador(view: Context){
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch{
                val colaboradores = getColaboradoresBusqueda(editTextNombre.text.toString())
                withContext(Dispatchers.Main){
                    adap = Colaboradores_Adapter(colaboradores)
                    rv.adapter=adap
                    rv.layoutManager = LinearLayoutManager(view)
                    progressBar.visibility= View.GONE
                }
            }
        }

    }