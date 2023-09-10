package com.techsphere.asociaplan.view

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.UI.fragments.DatePickerFragment
import com.techsphere.asociaplan.controller.registerEventoInBD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Locale.Category

class register_event : AppCompatActivity() {
    private lateinit var txtNombre : EditText
    private lateinit var txtFecha : EditText
    private lateinit var txtLugar : EditText
    private lateinit var txtDuracion : EditText
    private lateinit var txtCategoria : EditText
    private lateinit var txtCapacidad : EditText
    private lateinit var txtRequerimientos : EditText
    private lateinit var txtDescripcion : EditText
    private lateinit var btnRegis : Button
    private var fecha: java.util.Date? =null
    private var Id : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_event)

        Id = AuthHelper(this).getAccountId()
        txtNombre = findViewById(R.id.event_name)
        txtFecha = findViewById(R.id.date)
        txtLugar = findViewById(R.id.place)
        txtDuracion = findViewById(R.id.duration)
        txtCategoria = findViewById(R.id.category)
        txtCapacidad = findViewById(R.id.capacity)
        txtRequerimientos = findViewById(R.id.requirements)
        txtDescripcion = findViewById(R.id.description)
        btnRegis = findViewById(R.id.button_register)

        txtFecha.setOnClickListener {
            showDatePickerDialog()
        }

        btnRegis.setOnClickListener {
            Registrar()
        }
    }
        fun Registrar() {
            val Nombre = txtNombre.text.toString()
            val Fecha = txtFecha.text.toString()
            val Lugar = txtLugar.text.toString()
            val Duracion = txtDuracion.text.toString()
            val Categoria = txtCategoria.text.toString()
            val Capacidad = txtCapacidad.text.toString()
            val Requerimientos = txtRequerimientos.text.toString()
            val Descripcion = txtDescripcion.text.toString()

            txtDescripcion.error=null
            txtNombre.error=null
            txtFecha.error=null
            txtLugar.error=null
            txtDuracion.error=null
            txtCategoria.error=null
            txtCapacidad.error=null
            txtRequerimientos.error=null
            if (Nombre.isEmpty()||Nombre.isBlank()){
                txtNombre.error="Por favor introduzca el nombre"
                return
            }
            if (Fecha.isEmpty()||Fecha.isBlank()){
                txtFecha.error="Por favor introduzca la fecha"
                return
            }
            if (Lugar.isEmpty()||Lugar.isBlank()){
                txtLugar.error="Por favor introduzca el lugar"
                return
            }
            if (Duracion.isEmpty()||Duracion.isBlank()){
                txtDuracion.error="Por favor introduzca la duración"
                return
            }
            if (Categoria.isEmpty()||Categoria.isBlank()){
                txtCategoria.error="Por favor introduzca la categoria"
                return
            }
            if (Requerimientos.isEmpty()||Requerimientos.isBlank()){
                txtRequerimientos.error="Por favor introduzca los requerimientos"
                return
            }
            if (Capacidad.isEmpty()||Capacidad.isBlank()){
                txtCapacidad.error="Por favor introduzca la capacidad"
                return
            }
            if (Descripcion.isEmpty()||Descripcion.isBlank()){
                txtDescripcion.error="Por favor introduzca la descripcion"
                return
            }

            Toast.makeText(this, "Registrando el evento", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                val res = registerEventoInBD(Id!!,Nombre,fecha as java.sql.Date,Descripcion,Lugar,Duracion.toInt(),Requerimientos,Categoria)
                if (res == 1){
                    val intent = Intent(this@register_event,events::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Se le muestra el dialogo al usuario
                    withContext(Dispatchers.Main){
                        // El withContext se usa para llamar funciones que solo se pueden llamar
                        Toast.makeText(this@register_event,"No se registro el evento.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day: Int, month: Int, year: Int){
        txtFecha.setText("$day/${month+1}/$year")
        txtFecha.setText("Hola")
        fecha = GregorianCalendar(year, month, day).getTime()
    }
}