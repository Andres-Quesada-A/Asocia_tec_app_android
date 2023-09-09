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
import com.techsphere.asociaplan.controller.RedactarRespuestaForoInBD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class create_reply : AppCompatActivity() {
    private lateinit var txtCuerpo : EditText
    private lateinit var btnPublicar : Button
    private lateinit var btnCancelar : Button
    private var IdUsuario : Int? = null
    var idmensaje = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reply)
        idmensaje = (intent?.extras?.getInt("id")) as Int
        IdUsuario = AuthHelper(this).getAccountId()
        txtCuerpo = findViewById(R.id.txt_content)
        btnPublicar = findViewById(R.id.button_post)
        btnCancelar = findViewById(R.id.button_cancel)

        btnPublicar.setOnClickListener {
            Publicar()
        }
        btnCancelar.setOnClickListener{
            val intent = Intent(this,message_forum::class.java)
            intent.putExtra("id", idmensaje.toInt())
            this.startActivity(intent)
            (this as Activity).finish()
        }
    }

    fun Publicar() {
        val Cuerpo = txtCuerpo.text.toString()
        txtCuerpo.error=null
        if (Cuerpo.isEmpty()||Cuerpo.isBlank()){
            txtCuerpo.error="Por favor introduzca la redacción"
            return
        }

        Toast.makeText(this, "Publicando respuesta", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = RedactarRespuestaForoInBD("", Cuerpo,idmensaje,IdUsuario!!)
            if (res == 1){
                val intent = Intent(this@create_reply,message_forum::class.java)
                intent.putExtra("id", idmensaje.toInt())
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@create_reply,"No se publico la respuesta.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}