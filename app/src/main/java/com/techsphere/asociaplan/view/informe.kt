package com.techsphere.asociaplan.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Evaluaciones_Evento_Adapter
import com.techsphere.asociaplan.UI.adapters.Eventos_Informe_Adapter
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getAllEventosBD
import com.techsphere.asociaplan.controller.getEvaluacionesEventoDB
import com.techsphere.asociaplan.controller.getEventosBusqueda
import com.techsphere.asociaplan.controller.getInformeEventoDB
import com.techsphere.asociaplan.models.Eventos_Asociacion
import com.techsphere.asociaplan.models.Informe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class informe : AppCompatActivity() {
    private lateinit var rv : RecyclerView
    private lateinit var adap : Evaluaciones_Evento_Adapter
    private lateinit var progressBar : ProgressBar
    private lateinit var textoTitulo : TextView
    private lateinit var textoFecha : TextView
    private lateinit var textoParticipantes : TextView
    private lateinit var textoCapacidad : TextView
    private lateinit var textoCalificacion : TextView
    private lateinit var textoConf : TextView
    private lateinit var textoElim: TextView
    private lateinit var textoPend : TextView
    private var Id : Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informe)

        Id = (intent?.extras?.getInt("id")) as Int

        textoTitulo = findViewById<TextView>(R.id.title)
        textoFecha = findViewById<TextView>(R.id.date)
        textoParticipantes = findViewById<TextView>(R.id.participantes)
        textoCapacidad = findViewById<TextView>(R.id.capacidad)
        textoCalificacion = findViewById<TextView>(R.id.calificacion)
        textoConf = findViewById<TextView>(R.id.confirmadas)
        textoElim = findViewById<TextView>(R.id.eliminadas)
        textoPend = findViewById<TextView>(R.id.pendiantes)

        rv = findViewById<RecyclerView>(R.id.rv_evaluaciones_evento)

        progressBar = findViewById(R.id.progBarCubiEst)

        cargarInformacion()
        cargarEvaluaciones(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarInformacion() {
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val informe : MutableList<Informe> = getInformeEventoDB(Id)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (informe[0] != null) {
                    // Update the UI with the fetched assignment details
                    textoTitulo.setText("Titulo: ${informe[0].getTitulo()}")
                    textoFecha.setText("Fecha: ${informe[0].getFecha().toString()}")
                    textoParticipantes.setText("Participantes: ${informe[0].getParticipantes().toString()}")
                    textoCapacidad.setText("Capacidad: ${informe[0].getCapacidad().toString()}")
                    textoCalificacion.setText("Calificacion: ${informe[0].getCalificacion().toString()}")
                    textoConf.setText("Inscripciones confirmadas: ${informe[0].getConfirmadas().toString()}")
                    textoElim.setText("Inscripciones eliminadas: ${informe[0].getEliminadas().toString()}")
                    textoPend.setText("Inscripciones pendiantes: ${informe[0].getPendientes().toString()}")
                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }
    }

    fun cargarEvaluaciones(view: Context){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{
            val evaluaciones = getEvaluacionesEventoDB(Id)
            withContext(Dispatchers.Main){
                adap = Evaluaciones_Evento_Adapter(evaluaciones)
                rv.adapter=adap
                rv.layoutManager = LinearLayoutManager(view)
                progressBar.visibility= View.GONE
            }
        }
    }
}