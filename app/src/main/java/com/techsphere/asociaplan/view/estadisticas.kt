package com.techsphere.asociaplan.view

import android.service.autofill.Dataset
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import android.os.Bundle

import android.graphics.Color
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.journeyapps.barcodescanner.ViewfinderView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.getComentariosPorEventoDB
import com.techsphere.asociaplan.controller.getEstadisticasDB
import com.techsphere.asociaplan.controller.getEventosBusqueda
import com.techsphere.asociaplan.controller.getPromedioPorEventoDB
import com.techsphere.asociaplan.models.Estadisticas
import com.techsphere.asociaplan.models.Eventos_Asociacion
import com.techsphere.asociaplan.models.Tablas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class estadisticas : AppCompatActivity() {

    lateinit var entradas1: ArrayList<BarEntry>
    lateinit var barDataset1: BarDataSet
    lateinit var barData1: BarData
    lateinit var grafico1: BarChart

    lateinit var entradas2: ArrayList<BarEntry>
    lateinit var barDataset2: BarDataSet
    lateinit var barData2: BarData
    lateinit var grafico2: BarChart

    lateinit var textoNombre: TextView
    lateinit var textoEventos: TextView
    lateinit var textoActividades: TextView
    lateinit var textoEliminadas: TextView
    lateinit var textoPendientes: TextView
    lateinit var textoConfirmadas: TextView
    lateinit var textoParticipantes: TextView
    lateinit var textoEspacios: TextView
    lateinit var textoPropuestas: TextView
    lateinit var textoPromedio: TextView
    var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        textoNombre = findViewById(R.id.label_nombre)
        textoEventos = findViewById(R.id.label_cant_even)
        textoActividades = findViewById(R.id.label_cant_acti)
        textoEliminadas = findViewById(R.id.label_ins_eliminadas)
        textoPendientes = findViewById(R.id.label_ins_pendientes)
        textoConfirmadas = findViewById(R.id.label_ins_confirmadas)
        textoParticipantes = findViewById(R.id.label_total_participantes)
        textoEspacios = findViewById(R.id.label_espacios)
        textoPropuestas = findViewById(R.id.label_propuestas)
        textoPromedio = findViewById(R.id.label_promedio)

        grafico1 = findViewById(R.id.grafico1)
        grafico2 = findViewById(R.id.grafico2)

        entradas1 = ArrayList()
        entradas2 = ArrayList()

        val authHelper: AuthHelper = AuthHelper(this)
        idUsuario = authHelper.getAccountId()

        cargarEstadisticas()
        cargarComentarios()
        cargarPromedios()

        grafico1.getAxisRight().setEnabled(false)
        grafico2.getAxisRight().setEnabled(false)
        grafico1.getXAxis().setEnabled(true)
    }

    fun cargarEstadisticas(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val estadisticas : MutableList<Estadisticas> = getEstadisticasDB(idUsuario)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (estadisticas[0] != null) {
                    // Update the UI with the fetched assignment details
                    textoNombre.setText("Nombre: ${estadisticas[0].getNombre()}")
                    textoEventos.setText("Cantidad de eventos: ${estadisticas[0].getEventos().toString()}")
                    textoActividades.setText("Cantidad de actividades: ${estadisticas[0].getActividades().toString()}")
                    textoEliminadas.setText("Inscripciones eliminadas: ${estadisticas[0].getEliminadas().toString()}")
                    textoPendientes.setText("Inscripciones pendientes: ${estadisticas[0].getPendientes().toString()}")
                    textoConfirmadas.setText("Inscripciones confirmadas: ${estadisticas[0].getConfirmadas().toString()}")
                    textoParticipantes .setText("Total de participantes: ${estadisticas[0].getParticipantes().toString()}")
                    textoEspacios.setText("Total de espacios: ${estadisticas[0].getEspacios().toString()}")
                    textoPropuestas.setText("Cantidad de propuestas: ${estadisticas[0].getPropuestas().toString()}")
                    textoPromedio.setText("Calificacion promedio: ${estadisticas[0].getPromedio().toString()}")
                } else {
                    // Handle the case when the assignment is not found
                    // Show an error message or take appropriate action
                }
            }
        }

    }

    fun cargarComentarios(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val tablas : MutableList<Tablas> = getComentariosPorEventoDB(idUsuario)
            var largo = tablas.size
            var contador = 0
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (largo!=0){
                    while (contador<largo) {
                        entradas1.add(BarEntry(contador.toFloat(),tablas[contador].getCantidad().toFloat()))
                        contador++
                    }
                }else{
                    entradas1.add(BarEntry(0f,0f))
                }
                barDataset1 = BarDataSet(entradas1,"Comentarios")
                barData1 = BarData(barDataset1)
                grafico1.data = barData1
                barDataset1.setColors(ColorTemplate.MATERIAL_COLORS, 250)
                grafico1.invalidate()
            }
        }
    }
    fun cargarPromedios(){
        val dialogo = dialogs(this).showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val tablas : MutableList<Tablas> = getPromedioPorEventoDB(idUsuario)
            var largo = tablas.size
            var contador = 0
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (largo!=0){
                    while (contador<largo) {
                        entradas2.add(BarEntry(contador.toFloat(),tablas[contador].getCantidad().toFloat()))
                        contador++
                    }
                }else{
                    entradas2.add(BarEntry(0f,0f))
                }
                barDataset2 = BarDataSet(entradas2,"Promedios")
                barData2 = BarData(barDataset2)
                grafico2.data = barData2
                barDataset2.setColors(ColorTemplate.MATERIAL_COLORS, 250)
                grafico2.invalidate()
            }
        }
    }

}