package com.techsphere.asociaplan.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.EditarAsocInBD
import com.techsphere.asociaplan.controller.gestionarPropuestaDB
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import com.techsphere.asociaplan.controller.getBuscarPropuestaBD
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Propuesta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class informacion_propuesta : AppCompatActivity() {
    private lateinit var txtPromotor : TextView
    private lateinit var txtTematica : TextView
    private lateinit var txtObjetivos : TextView
    private lateinit var txtActividades : TextView
    private lateinit var txtDetalles : TextView
    private lateinit var btnAceptar : Button
    private lateinit var btnDenegar : Button
    private lateinit var btnVolver : Button
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_propuesta)
        id = (intent?.extras?.getInt("id")) as Int
        txtPromotor = findViewById(R.id.label_promotor)
        txtTematica = findViewById(R.id.tematica)
        txtObjetivos = findViewById(R.id.objetivos)
        txtActividades = findViewById(R.id.actividades)
        txtDetalles = findViewById(R.id.detalles)
        btnAceptar = findViewById(R.id.button_aceptar)
        btnDenegar = findViewById(R.id.button_descartar)
        btnVolver = findViewById(R.id.button_volver)
        btnAceptar.setOnClickListener {
            confirmarPropuesta()
        }
        btnDenegar.setOnClickListener {
            denegarPropuesta()
        }
        btnVolver.setOnClickListener {
            val intent = Intent(this,gestion_propuestas::class.java)
            startActivity(intent)
        }
        val authHelper: AuthHelper = AuthHelper(this)
        val idUsuario = authHelper.getAccountId()
        cargarPropuesta(idUsuario)
    }

    fun cargarPropuesta(idUsuario: Int){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val prop : MutableList<Propuesta> = getBuscarPropuestaBD(idUsuario, id)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (prop[0] != null) {
                    // Update the UI with the fetched assignment details
                    txtPromotor.setText("Promotor: "+prop[0].getPromotor())
                    txtTematica.setText(prop[0].getTematica())
                    txtObjetivos.setText(prop[0].getObjetivos())
                    txtActividades.setText(prop[0].getActividades())
                    txtDetalles.setText(prop[0].getDetalles())
                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }

    fun confirmarPropuesta(){
        Toast.makeText(this, "Aceptando la propuesta", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = gestionarPropuestaDB(id,3)
            txtDetalles.setText(res.toString())
            if (res == 1){
                startActivity(Intent(this@informacion_propuesta,gestion_propuestas::class.java))
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@informacion_propuesta,"No se acepto la propuesta.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun denegarPropuesta(){
        Toast.makeText(this, "Denegando la propuesta", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = gestionarPropuestaDB(id,1 )
            if (res == 1){
                startActivity(Intent(this@informacion_propuesta,gestion_propuestas::class.java))
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@informacion_propuesta,"No se denego la propuesta.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}