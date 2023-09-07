package com.techsphere.asociaplan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.controller.EditarAsocInBD
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.models.Asociacion

class editar_asociacion : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtCorreo : EditText
    private lateinit var txtContraseña : EditText
    private lateinit var txtContacto : EditText
    private lateinit var txtCod : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnRegis : Button
    var nombre = ""
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_asociacion)

        nombre = (intent?.extras?.getString("nombre")) as String
        id = (intent?.extras?.getInt("id")) as Int
        txtNombre = findViewById(R.id.nombre_asociacion)
        txtContacto = findViewById(R.id.contacto)
        txtCod = findViewById(R.id.codigo_carrera)
        txtDescripcion = findViewById(R.id.descripcion)
        txtContraseña = findViewById(R.id.txt_contrasena)
        txtCorreo = findViewById(R.id.txt_correo)
        btnRegis = findViewById(R.id.button_registrar)
        btnRegis.setOnClickListener {
            Actualizar()
        }

        cargarAsociacion()
    }

    fun Actualizar() {
        val Nombre = txtNombre.text.toString()
        val Contacto = txtContacto.text.toString()
        val Correo = txtCorreo.text.toString()
        val Contraseña = txtContraseña.toString()
        val Codigo = txtCod.text.toString()
        val Descripcion = txtDescripcion.text.toString()
        txtCod.error=null
        txtDescripcion.error=null
        txtContacto.error=null
        txtCorreo.error=null
        txtContraseña.error=null
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
        if (Contraseña.isEmpty()||Contraseña.isBlank()){
            txtContraseña.error="Por favor introduzca la contraseña"
            return
        }

        Toast.makeText(this, "Editando la asociación", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = EditarAsocInBD(id, Nombre, Contacto,
                Codigo, Descripcion, Correo, Contraseña
            )
            if (res == 1){
                startActivity(Intent(this@editar_asociacion,asociaciones::class.java))
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@editar_asociacion,"No se edito la asociación.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun cargarAsociacion(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val asig : MutableList<Asociacion> = getAsociacionesBusqueda(nombre)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (asig[0] != null) {
                    // Update the UI with the fetched assignment details
                    txtNombre.setText(asig[0].getNombre())
                    txtContacto.setText(asig[0].getContacto())
                    txtCod.setText(asig[0].getCodigo())
                    txtDescripcion.setText(asig[0].getDescripcion())
                    txtContraseña.setText(asig[0].getContrasena())
                    txtCorreo.setText(asig[0].getCorreo())

                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }

}