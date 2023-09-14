package com.techsphere.asociaplan.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.fragments.DatePickerFragment
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.registerEventoInBD
import com.techsphere.asociaplan.controller.registrarActividad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.GregorianCalendar

class registrar_actividad : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtInicio : EditText
    private lateinit var txtFin : EditText
    private lateinit var txtUbicaicon : EditText
    private lateinit var txtRecursos : EditText
    private lateinit var btnRegis : Button
    private var fecha: Date? =null
    private var fecha2: Date? =null
    private var Id : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_actividad)

        Id = (intent?.extras?.getInt("id")) as Int
        txtNombre = findViewById(R.id.nombre)
        txtInicio = findViewById(R.id.inicio)
        txtFin = findViewById(R.id.fin)
        txtUbicaicon = findViewById(R.id.ubicaicon)
        txtRecursos = findViewById(R.id.recursos)
        btnRegis = findViewById(R.id.button_register)

        txtInicio.setOnClickListener {
            showDatePickerDialog()
        }
        txtFin.setOnClickListener {
            showDatePickerDialog2()
        }
        btnRegis.setOnClickListener {
            Registrar()
        }
    }
    fun Registrar() {
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
        Toast.makeText(this, "Registrando el evento", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val res = registrarActividad(Id,java.sql.Date((fecha as Date).time),
                                            java.sql.Date((fecha2 as Date).time),
                                            Ubicaion,Recursos,Nombre)
            if (res == 1){
                val intent = Intent(this@registrar_actividad,actividades::class.java)
                intent.putExtra("id",Id)
                startActivity(intent)
                finish()
            } else {
                // Se le muestra el dialogo al usuario
                withContext(Dispatchers.Main){
                    // El withContext se usa para llamar funciones que solo se pueden llamar
                    Toast.makeText(this@registrar_actividad,"No se registro la actividad.", Toast.LENGTH_SHORT).show()
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
}