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
import com.techsphere.asociaplan.models.Colaborador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class collaborator_details : AppCompatActivity() {
    private lateinit var txtNombre : MaterialTextView
    private lateinit var txtContacto : MaterialTextView
    private lateinit var txtDescripcion : MaterialTextView
    private lateinit var btnVolver : Button
    var nombre = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collaborator_details)
        nombre = (intent?.extras?.getString("nombre")) as String
        txtNombre = findViewById(R.id.name)
        txtNombre.setEnabled(false);
        txtContacto = findViewById(R.id.contact)
        txtContacto.setEnabled(false);
        txtDescripcion = findViewById(R.id.description)
        txtDescripcion.setEnabled(false);
        cargarColaborador()
        btnVolver = findViewById(R.id.button_back)

        btnVolver.setOnClickListener {
            val intent = Intent(this,collaborator_list::class.java)
            this.startActivity(intent)
            (this as Activity).finish()
        }
    }

    fun cargarColaborador(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val asig : MutableList<Colaborador> = getColaboradoresBusqueda(nombre)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (asig[0] != null) {
                    // Update the UI with the fetched assignment details
                    txtNombre.setText("Nombre: ${asig[0].getNombre()}")
                    txtContacto.setText("Contacto: ${asig[0].getContacto()}")
                    txtDescripcion.setText("Descripción: ${asig[0].getDescripcion()}")

                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }
}