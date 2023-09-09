package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.controller.EditarColaboradorInBD
import com.techsphere.asociaplan.controller.getColaboradoresBusqueda
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Colaborador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class edit_collaborator : AppCompatActivity() {
    private lateinit var txtContacto : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnActualizar : Button
    var id = 0
    var nombre = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_collaborator)
        id = (intent?.extras?.getInt("id")) as Int
        nombre = (intent?.extras?.getString("nombre")) as String
        txtContacto = findViewById(R.id.contacto)
        txtDescripcion = findViewById(R.id.descripcion)
        cargarColaborador()
        btnActualizar = findViewById(R.id.button_update)

        btnActualizar.setOnClickListener {
            Actualizar()
        }
    }
    fun Actualizar() {
        val Contacto = txtContacto.text.toString()
        val Descripcion = txtDescripcion.text.toString()
        txtDescripcion.error=null
        txtContacto.error=null

        Toast.makeText(this, "Actualizando al colaborador", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = EditarColaboradorInBD(id, Descripcion, Contacto)
            if (res == 1){
                val intent = Intent(this@edit_collaborator,collaborator_list::class.java)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@edit_collaborator,"No se registro el colaborador.", Toast.LENGTH_SHORT).show()
                }
            }
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
                    txtContacto.setText(asig[0].getContacto())
                    txtDescripcion.setText(asig[0].getDescripcion())

                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }
}