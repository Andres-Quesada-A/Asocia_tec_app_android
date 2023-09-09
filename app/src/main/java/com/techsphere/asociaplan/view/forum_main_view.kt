package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Foro_Items_Adapter
import com.techsphere.asociaplan.controller.getAllForoItemsBD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class forum_main_view : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Foro_Items_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var RedactarButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_main_view)
        RedactarButton = findViewById<Button>(R.id.button_redactar)

        rv = findViewById<RecyclerView>(R.id.rv_foro)

        progressBar = findViewById(R.id.progBarCubiEst)

        cargarItems(this)
        RedactarButton.setOnClickListener {
            val intent = Intent(this,create_forum::class.java)
            startActivity(intent)
        }
    }
    fun cargarItems(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val redacciones = getAllForoItemsBD()
            withContext(Dispatchers.Main){
                adap = Foro_Items_Adapter(redacciones)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
}