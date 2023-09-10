package com.techsphere.asociaplan.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.EnviarEvaluacionDB
import com.techsphere.asociaplan.controller.EnviarPropuestaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class evaluar_evento : AppCompatActivity() {
    private lateinit var editComentario : EditText
    private lateinit var nombre : TextView
    private lateinit var editCalificacion: NumberPicker
    private lateinit var Enviar : Button
    private lateinit var volver : Button
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluar_evento)
        nombre = findViewById<TextView>(R.id.label_nombre)
        editComentario = findViewById<EditText>(R.id.objetivos)
        editCalificacion = findViewById<NumberPicker>(R.id.calificacion)
        Enviar = findViewById<Button>(R.id.button_enviar)
        volver = findViewById<Button>(R.id.button_volver)
        editCalificacion.setMinValue(1)
        editCalificacion.setMaxValue(5)
        val authHelper: AuthHelper = AuthHelper(this)
        val bundle = intent.getExtras()
        val txtNombre = bundle?.getString("nombre")!!
        nombre.setText("Nombre del evento: "+txtNombre)
        Enviar.setOnClickListener {
            val idEvento = bundle?.getInt("id")!!
            val idUsuario = authHelper.getAccountId()
            EvaluarEvento(idEvento, idUsuario)
        }
        volver.setOnClickListener {
            finish()
        }
    }
    fun EvaluarEvento(idEven: Int, idUsu: Int){
        val textoComentario = editComentario.text.toString()
        val textCalificacion = editCalificacion.value
        editComentario.error=null
        if (textoComentario.isEmpty()||textoComentario.isBlank()){
            editComentario.error="Por favor introduzca un comentario"
            return
        }
        Toast.makeText(this, "Enviando evaluacion", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = EnviarEvaluacionDB(textoComentario, textCalificacion, idEven, idUsu)
            if (res == 1){
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@evaluar_evento,"No se envio la evaluacion.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}