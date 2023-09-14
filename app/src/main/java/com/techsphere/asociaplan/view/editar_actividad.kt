package com.techsphere.asociaplan.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.UI.fragments.DatePickerFragment
import com.techsphere.asociaplan.controller.editarActividad
import com.techsphere.asociaplan.controller.getActividadIdBusqueda
import com.techsphere.asociaplan.models.Actividad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.util.Date
import java.util.GregorianCalendar

class editar_actividad : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtInicio : EditText
    private lateinit var txtFin : EditText
    private lateinit var txtUbicaicon : EditText
    private lateinit var txtRecursos : EditText
    private lateinit var btnRegis : Button
    private lateinit var labelNombre: TextView
    private var fecha: Date? =null
    private var fecha2: Date? =null
    private var Id : Int = 0
    private var idActividad : Int = 0
    private var idEvento: Int = 0
    private var nombre: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_actividad)

        Id = (intent?.extras?.getInt("id")) as Int
        nombre = (intent?.extras?.getString("nombre")) as String
        idEvento = (intent?.extras?.getInt("idEvento")) as Int
        txtNombre = findViewById(R.id.nombre)
        txtInicio = findViewById(R.id.inicio)
        txtFin = findViewById(R.id.fin)
        txtUbicaicon = findViewById(R.id.ubicaicon)
        txtRecursos = findViewById(R.id.recursos)
        btnRegis = findViewById(R.id.button_register)
        labelNombre = findViewById(R.id.label_nombre)
        labelNombre.setText("Nombre: "+nombre)
        CargarActividad()
        txtInicio.setOnClickListener {
            showDatePickerDialog()
        }
        txtFin.setOnClickListener {
            showDatePickerDialog2()
        }
        btnRegis.setOnClickListener {
            Editar()
        }
    }
    fun Editar() {
        val Nombre = txtNombre.text.toString()
        val Inicio = txtInicio.text.toString()
        val Fin = txtFin.text.toString()
        val Ubicaion = txtUbicaicon.text.toString()
        val Recursos = txtRecursos.text.toString()

        txtNombre.error=null
        txtInicio.error=null
        txtFin.error=null
        txtUbicaicon.error=null
        txtRecursos.error=null
        if (Nombre.isEmpty()||Nombre.isBlank()){
            txtNombre.error="Por favor introduzca el nombre"
            return
        }
        if (Inicio.isEmpty()||Inicio.isBlank()){
            txtInicio.error="Por favor introduzca la fecha inicial"
            return
        }
        if (Fin.isEmpty()||Fin.isBlank()){
            txtFin.error="Por favor introduzca la fecha final"
            return
        }
        if (Ubicaion.isEmpty()||Ubicaion.isBlank()){
            txtUbicaicon.error="Por favor introduzca la ubicacion"
            return
        }
        if (Recursos.isEmpty()||Recursos.isBlank()){
            txtRecursos.error="Por favor introduzca los recursos"
            return
        }
        Toast.makeText(this, "Editando la actividad", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = editarActividad(idActividad,java.sql.Date((fecha as Date).time),
                java.sql.Date((fecha2 as Date).time),
                Ubicaion,Recursos,Nombre)
            if (res == 1){
                val intent = Intent(this@editar_actividad, actividades::class.java)
                intent.putExtra("id",idEvento)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@editar_actividad,"No se editado la actividad.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int){
        txtInicio.setText("$day/${month+1}/$year")
        fecha = GregorianCalendar(year, month, day).getTime()
    }
    private fun showDatePickerDialog2() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected2(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected2(day: Int, month: Int, year: Int){
        txtFin.setText("$day/${month+1}/$year")
        fecha2 = GregorianCalendar(year, month, day).getTime()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun CargarActividad(){
        val diag = dialogs(this)
        val dialogo = diag.showLoadingDialog()
        dialogo.show()
        CoroutineScope(Dispatchers.IO).launch {
            //Se esta usando una lista para salir del paso y no perder mucho tiempo
            //modificando el controlador (que esta basado en los otros ya existentes)
            val actividad : MutableList<Actividad> = getActividadIdBusqueda(Id)
            // Update the UI with the fetched assignment details
            dialogo.dismiss()
            withContext(Dispatchers.Main) {
                if (actividad.size!=0){
                    if (actividad[0] != null) {
                        // Update the UI with the fetched assignment details
                        txtNombre.setText(actividad[0].getNombre())
                        txtInicio.setText(actividad[0].getFechaToString())
                        fecha = java.sql.Date.from(actividad[0].getInicio().atStartOfDay(ZoneId.systemDefault()).toInstant())
                        txtFin.setText(actividad[0].getFechaFinToString())
                        fecha2 = java.sql.Date.from(actividad[0].getFin().atStartOfDay(ZoneId.systemDefault()).toInstant())
                        txtUbicaicon.setText(actividad[0].getUbicacion())
                        txtRecursos.setText(actividad[0].getRecursos())
                        idActividad = actividad[0].getId()
                    } else {
                        // Handle the case when the assignment is not found
                        // Show an error message or take appropriate action
                        diag.showErrorDialog(99)
                    }
                } else {
                    diag.showErrorDialog(99)
                }

            }
        }
    }
}