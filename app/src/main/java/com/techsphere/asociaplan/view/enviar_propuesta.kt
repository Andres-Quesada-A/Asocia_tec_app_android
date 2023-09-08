package com.techsphere.asociaplan.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Asociaciones_Propuestas_Adapter
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.EditarAsocInBD
import com.techsphere.asociaplan.controller.EnviarPropuestaDB
import com.techsphere.asociaplan.controller.getAllAsociacionesBD
import com.techsphere.asociaplan.controller.getAsociacionesBusqueda
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class enviar_propuesta : AppCompatActivity() {
    private lateinit var editTextTematica : EditText
    private lateinit var editTextObjetivos : EditText
    private lateinit var editTextActividades : EditText
    private lateinit var editTextDetalles : EditText
    private lateinit var Enviar : Button
    private lateinit var volver : Button
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_propuesta)
        editTextTematica = findViewById<EditText>(R.id.tematica)
        editTextObjetivos = findViewById<EditText>(R.id.objetivos)
        editTextActividades = findViewById<EditText>(R.id.actividades)
        editTextDetalles = findViewById<EditText>(R.id.detalles)
        Enviar = findViewById<Button>(R.id.button_enviar)
        volver = findViewById<Button>(R.id.button_volver)

        Enviar.setOnClickListener {
            val bundle = intent.getExtras()
            val idAsociacion = bundle?.getInt("id")!!
            val authHelper: AuthHelper = AuthHelper(this)
            val idUsuario = authHelper.getAccountId()
            EnviarPropuesta(idAsociacion, idUsuario)
        }
        volver.setOnClickListener {
            val intent = Intent(this,asociaciones_propuesta::class.java)
            startActivity(intent)
        }
    }
    fun EnviarPropuesta(idAso: Int, idEven: Int){
        val textoTematica = editTextTematica.text.toString()
        val textoObjetivos = editTextObjetivos.text.toString()
        val textoActividades = editTextActividades.text.toString()
        val textoDetalles = editTextDetalles.text.toString()
        editTextTematica.error=null
        editTextObjetivos.error=null
        editTextActividades.error=null
        editTextDetalles.error=null
        if (textoTematica.isEmpty()||textoTematica.isBlank()){
            editTextTematica.error="Por favor introduzca la tematica"
            return
        }
        if (textoObjetivos.isEmpty()||textoObjetivos.isBlank()){
            editTextObjetivos.error="Por favor introduzca los objetivos"
            return
        }
        if (textoActividades.isEmpty()||textoActividades.isBlank()){
            editTextActividades.error="Por favor introduzca las actividades"
            return
        }
        if (textoDetalles.isEmpty()||textoDetalles.isBlank()){
            editTextDetalles.error="Por favor introduzca algun detalle"
            return
        }
        Toast.makeText(this, "Enviando propuesta", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = EnviarPropuestaDB(textoTematica, textoObjetivos, textoActividades,
                textoDetalles, idAso, idEven
            )
            if (res == 1){
                startActivity(Intent(this@enviar_propuesta,enviar_propuesta::class.java))
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@enviar_propuesta,"No se envio la propuesta.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}