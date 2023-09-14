package com.techsphere.asociaplan.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textview.MaterialTextView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.controller.EditarColaboradorInBD
import com.techsphere.asociaplan.controller.getColaboradoresBusqueda
import com.techsphere.asociaplan.controller.getEstudiantesBusqueda
import com.techsphere.asociaplan.models.Colaborador
import com.techsphere.asociaplan.models.Estudiantes_Admin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class estudiante_details : AppCompatActivity() {
    private lateinit var txtNombre : MaterialTextView
    private lateinit var txtCorreo : MaterialTextView
    private lateinit var txtCarne : MaterialTextView
    private lateinit var txtArea : MaterialTextView
    private lateinit var txtContrasena : MaterialTextView
    private lateinit var btnVolver : Button
    var nombre = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante_details)
        nombre = (intent?.extras?.getString("nombre")) as String
        txtNombre = findViewById(R.id.name)
        txtNombre.setEnabled(false);
        txtCorreo = findViewById(R.id.Correo)
        txtCorreo.setEnabled(false);
        txtCarne = findViewById(R.id.Carne)
        txtCarne.setEnabled(false);
        txtArea = findViewById(R.id.Area)
        txtArea.setEnabled(false);
        txtContrasena = findViewById(R.id.Contrasena)
        txtContrasena.setEnabled(false);
        cargarEstudiante()
        btnVolver = findViewById(R.id.button_back)

        btnVolver.setOnClickListener {
            val intent = Intent(this,estudiantes_list::class.java)
            this.startActivity(intent)
            (this as Activity).finish()
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
                    txtNombre.setText("Nombre: ${asig[0].getNombre()}")
                    txtCorreo.setText("Correo: ${asig[0].getCorreo()}")
                    txtCarne.setText("Carné institucional: ${asig[0].getCarne()}")
                    txtArea.setText("Área de estudio: ${asig[0].getArea()}")
                    txtContrasena.setText("Contraseña: ${asig[0].getContrasena()}")

                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }
}