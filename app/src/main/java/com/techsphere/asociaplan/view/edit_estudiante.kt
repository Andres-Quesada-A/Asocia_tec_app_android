package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.controller.EditarEstudianteInBD
import com.techsphere.asociaplan.controller.getEstudiantesBusqueda
import com.techsphere.asociaplan.models.Colaborador
import com.techsphere.asociaplan.models.Estudiantes_Admin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class edit_estudiante : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtCorreo : EditText
    private lateinit var txtCarne : EditText
    private lateinit var txtArea : EditText
    private lateinit var txtContrasena : EditText
    private lateinit var btnActualizar : Button
    var id = 0
    var nombre = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_estudiante)
        id = (intent?.extras?.getInt("id")) as Int
        nombre = (intent?.extras?.getString("nombre")) as String
        txtNombre = findViewById(R.id.input_nombre)
        txtCorreo = findViewById(R.id.input_correo)
        txtCarne = findViewById(R.id.input_Carne)
        txtArea = findViewById(R.id.input_area_estudio)
        txtContrasena = findViewById(R.id.input_contrasena)
        cargarEstudiante()
        btnActualizar = findViewById(R.id.button_update)

        btnActualizar.setOnClickListener {
            Actualizar()
        }
    }
    fun Actualizar() {
        val Nombre = txtNombre.text.toString()
        val Correo = txtCorreo.text.toString()
        val Carne = txtCarne.text.toString()
        val Area = txtArea.text.toString()
        val Contrasena = txtContrasena.text.toString()
        txtNombre.error=null
        txtCorreo.error=null
        txtCarne.error=null
        txtArea.error=null
        txtContrasena.error=null

        Toast.makeText(this, "Actualizando al Estudiante", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = EditarEstudianteInBD(id, Nombre, Correo,Carne.toInt(),Area,Contrasena)
            if (res == 1){
                val intent = Intent(this@edit_estudiante,estudiantes_list::class.java)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@edit_estudiante,"No se actualizo el estudiante.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun cargarEstudiante(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val asig : MutableList<Estudiantes_Admin> = getEstudiantesBusqueda(nombre)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (asig[0] != null) {
                    // Update the UI with the fetched assignment details
                    txtNombre.setText(asig[0].getNombre())
                    txtCorreo.setText(asig[0].getCorreo())
                    txtCarne.setText(asig[0].getCarne().toString())
                    txtArea.setText(asig[0].getArea())
                    txtContrasena.setText(asig[0].getContrasena())

                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }
}