package com.techsphere.asociaplan.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.RedactarForoInBD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class create_forum : AppCompatActivity() {

    private lateinit var txtTitulo : EditText
    private lateinit var txtCuerpo : EditText
    private lateinit var btnPublicar : Button
    private lateinit var btnCancelar : Button
    private var Id : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_forum)
        Id = AuthHelper(this).getAccountId()
        txtTitulo = findViewById(R.id.txt_title)
        txtCuerpo = findViewById(R.id.txt_content)
        btnPublicar = findViewById(R.id.button_post)
        btnCancelar = findViewById(R.id.button_cancel)

        btnCancelar.setOnClickListener{
            val intent = Intent(this,forum_main_view::class.java)
            this.startActivity(intent)
            (this as Activity).finish()
        }
        btnPublicar.setOnClickListener {
            Publicar()
        }
    }

    fun Publicar() {
        val Titulo = txtTitulo.text.toString()
        val Cuerpo = txtCuerpo.text.toString()
        txtTitulo.error=null
        txtCuerpo.error=null
        if (Titulo.isEmpty()||Titulo.isBlank()){
            txtTitulo.error="Por favor introduzca el Titulo"
            return
        }
        if (Cuerpo.isEmpty()||Cuerpo.isBlank()){
            txtCuerpo.error="Por favor introduzca la redacción"
            return
        }

        Toast.makeText(this, "Publicando", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = RedactarForoInBD(Titulo, Cuerpo,Id!!)
            if (res == 1){
                val intent = Intent(this@create_forum,forum_main_view::class.java)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@create_forum,"No se publico.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}