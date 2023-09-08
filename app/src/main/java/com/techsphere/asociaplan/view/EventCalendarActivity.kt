package com.techsphere.asociaplan.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Eventos_Calendario_Adapter
import com.techsphere.asociaplan.controller.Administrador
import com.techsphere.asociaplan.controller.EventosController
import com.techsphere.asociaplan.models.Eventos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.time.LocalDate


class EventCalendarActivity : AppCompatActivity() {
    private lateinit var calendar : CalendarView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: Eventos_Calendario_Adapter
    private lateinit var progressBar: ProgressBar
    private lateinit var eventosArray: MutableList<Eventos>
    private var eventosCtrl = EventosController()
    private var admin = Administrador()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_calendar)
        rv = findViewById(R.id.recyclerViewEvents)
        progressBar = findViewById(R.id.progBar)
        progressBar.visibility= View.INVISIBLE
        calendar = findViewById(R.id.calendarView)
        calendar.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, day ->
            mostrarEventosFecha(year, month, day)
        })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun mostrarEventosFecha(anno: Int, mes: Int, dia: Int){
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
            //Hay que añadir un texto que aparezca cuando no hayan eventos
            withContext(Dispatchers.IO){
                eventosArray=admin.getEventosFecha(Date.valueOf(LocalDate.of(anno, mes+1, dia).toString()))
            }
            adapter= Eventos_Calendario_Adapter(eventosArray)
            rv.adapter=adapter
            rv.layoutManager=LinearLayoutManager(this@EventCalendarActivity)
            progressBar.visibility = View.INVISIBLE
        }

    }
}