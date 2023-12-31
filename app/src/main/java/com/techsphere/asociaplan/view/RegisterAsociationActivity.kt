package com.techsphere.asociaplan.view

import android.content.Context
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

class RegisterAsociationActivity : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtCorreo : EditText
    private lateinit var txtContrasena : EditText
    private lateinit var txtContacto : EditText
    private lateinit var txtCod : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnRegis : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_asociacion)
        txtNombre = findViewById(R.id.nombre_asociacion)
        txtContacto = findViewById(R.id.contacto)
        txtCod = findViewById(R.id.codigo_carrera)
        txtDescripcion = findViewById(R.id.descripcion)
        txtContrasena = findViewById(R.id.txt_contrasena)
        txtCorreo = findViewById(R.id.txt_correo)
        btnRegis = findViewById(R.id.button_registrar)

        btnRegis.setOnClickListener {
            Registrar()
        }
    }

    fun Registrar() {
        val Nombre = txtNombre.text.toString()
        val Contacto = txtContacto.text.toString()
        val Correo = txtCorreo.text.toString()
        val Contrasena = txtContrasena.text.toString()
        val Codigo = txtCod.text.toString()
        val Descripcion = txtDescripcion.text.toString()
        txtCod.error=null
        txtDescripcion.error=null
        txtContacto.error=null
        txtCorreo.error=null
        txtContrasena.error=null
        txtNombre.error=null
        if (Nombre.isEmpty()||Nombre.isBlank()){
            txtNombre.error="Por favor introduzca el nombre"
            return
        }
        if (Contacto.isEmpty()||Contacto.isBlank()){
            txtContacto.error="Por favor introduzca el contacto"
            return
        }
        if (Codigo.isEmpty()||Codigo.isBlank()){
            txtCod.error="Por favor introduzca el Codigo"
            return
        }
        if (Descripcion.isEmpty()||Descripcion.isBlank()){
            txtDescripcion.error="Por favor introduzca la descripcion"
            return
        }
        if (Correo.isEmpty()||Correo.isBlank()){
            txtCorreo.error="Por favor introduzca el Correo"
            return
        }
        if (Contrasena.isEmpty()||Contrasena.isBlank()){
            txtContrasena.error="Por favor introduzca la contraseña"
            return
        }

        Toast.makeText(this, "Registrando la asociación", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = registerAsocInBD(Nombre, Contacto,
                Codigo, Descripcion, Correo, Contrasena
            )
            if (res == 1){
                val intent = Intent(this@RegisterAsociationActivity,asociaciones::class.java)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@RegisterAsociationActivity,"No se registro la asociación.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}