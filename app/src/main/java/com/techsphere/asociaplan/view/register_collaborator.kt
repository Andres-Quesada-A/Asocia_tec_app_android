package com.techsphere.asociaplan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent
import kotlinx.coroutines.withContext
import com.techsphere.asociaplan.controller.registerAsocInBD

class register_collaborator : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtContacto : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnRegis : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_collaborator)
        txtNombre = findViewById(R.id.collaborator_name)
        txtContacto = findViewById(R.id.contact)
        txtDescripcion = findViewById(R.id.description)
        btnRegis = findViewById(R.id.button_register)

        btnRegis.setOnClickListener {
            Registrar()
        }
    }

    fun Registrar() {
        val Nombre = txtNombre.text.toString()
        val Contacto = txtContacto.text.toString()
        val Descripcion = txtDescripcion.text.toString()
        txtDescripcion.error=null
        txtContacto.error=null
        txtNombre.error=null
        if (Nombre.isEmpty()||Nombre.isBlank()){
            txtNombre.error="Por favor introduzca el nombre"
            return
        }
        if (Contacto.isEmpty()||Contacto.isBlank()){
            txtContacto.error="Por favor introduzca el contacto"
            return
        }
        if (Descripcion.isEmpty()||Descripcion.isBlank()){
            txtDescripcion.error="Por favor introduzca la descripcion"
            return
        }

        /*Toast.makeText(this, "Registrando la colaborador", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = registerCollabInBD(Nombre, Contacto, Descripcion
            )
            if (res == 1){
                val intent = Intent(this@register_collaborator,menu::class.java)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@register_collaborator,"No se registro el colaborador.", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
    }
}