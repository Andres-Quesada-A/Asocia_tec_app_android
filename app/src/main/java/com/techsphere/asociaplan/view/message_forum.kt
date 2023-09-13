package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Foro_Respuestas_Adapter
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.controller.getAllForoRespuestasBD
import com.techsphere.asociaplan.controller.getForoItemsBD
import com.techsphere.asociaplan.models.ForoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class message_forum : AppCompatActivity() {
    private lateinit var txtTitulo : MaterialTextView
    private lateinit var txtCuerpo : MaterialTextView
    private lateinit var txtAutor: TextView
    private lateinit var rv : RecyclerView
    private lateinit var adap : Foro_Respuestas_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var RedactarButton : Button
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_forum)
        id = (intent?.extras?.getInt("id")) as Int
        RedactarButton = findViewById<Button>(R.id.button_reply)
        txtAutor = findViewById(R.id.txtAutor)
        rv = findViewById<RecyclerView>(R.id.rv_replies)

        progressBar = findViewById(R.id.progBarCubiEst)
        txtTitulo = findViewById(R.id.title_forum)
        txtTitulo.setEnabled(false);
        txtCuerpo = findViewById(R.id.content_forum)
        txtCuerpo.setEnabled(false);
        cargarRedaccion()
        RedactarButton.setOnClickListener {
            val intent = Intent(this,create_reply::class.java)
            intent.putExtra("id", id.toInt())
            startActivity(intent)
        }

    }
    fun cargarItems(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val respuestas = getAllForoRespuestasBD(id)
            withContext(Dispatchers.Main){
                adap = Foro_Respuestas_Adapter(respuestas)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
    fun cargarRedaccion(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val asig : MutableList<ForoItem> = getForoItemsBD(id)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (asig[0] != null) {
                    // Update the UI with the fetched assignment details
                    val post = asig[0]
                    txtTitulo.setText(post.getTitulo())
                    txtCuerpo.setText(post.getCuerpo())
                    txtAutor.setText(post.getAutor()+" publico:")
                    cargarItems(this@message_forum)
                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }
}