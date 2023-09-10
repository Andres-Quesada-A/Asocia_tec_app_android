package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Eventos_Evaluar_Adapter
import com.techsphere.asociaplan.UI.adapters.Gestion_Propuestas_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getAllPropuestasBD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class gestion_propuestas : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Gestion_Propuestas_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var volver : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_propuestas)
        volver = findViewById<Button>(R.id.button_volver)

        rv = findViewById<RecyclerView>(R.id.rvPropuestas)
        val authHelper: AuthHelper = AuthHelper(this)
        val idUsuario = authHelper.getAccountId()
        progressBar = findViewById(R.id.progBarCubiEst)
        cargarPropuestas(this,idUsuario)
        volver.setOnClickListener {
            val intent = Intent(this,menu_asociacion::class.java)
            startActivity(intent)
        }
    }
    fun cargarPropuestas(view: Context, idUsuario: Int){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val propuestas = getAllPropuestasBD(idUsuario)
            withContext(Dispatchers.Main){
                adap = Gestion_Propuestas_Adapter(propuestas)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }

}