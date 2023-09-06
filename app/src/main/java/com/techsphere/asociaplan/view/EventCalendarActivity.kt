package com.techsphere.asociaplan.view

import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.adapters.Eventos_Calendario_Adapter
import com.techsphere.asociaplan.models.Eventos
import java.time.LocalDateTime


class EventCalendarActivity : AppCompatActivity() {
    private lateinit var calendar : CalendarView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: Eventos_Calendario_Adapter
    private lateinit var progressBar: ProgressBar
    private var eventosArray = arrayOf<Eventos>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_calendar)
        rv = findViewById(R.id.recyclerViewEvents)
        progressBar = findViewById(R.id.progBar)
        progressBar.visibility= View.INVISIBLE
        val evento1 = Eventos("Evento 1", "Descripción 1", LocalDateTime.now(), "Lugar 1", 120, "a")
        val evento2 = Eventos("Evento 2", "Descripción 2", LocalDateTime.now(), "Lugar 2", 90, "a")
        val evento3 = Eventos("Evento 3", "Descripción 3", LocalDateTime.now(), "Lugar 3", 150, "a")
        eventosArray = arrayOf(evento1, evento2, evento3)
        calendar = findViewById(R.id.calendarView)
        calendar.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, day ->
            mostrarEventosFecha(year, month, day)
        })

    }
    fun mostrarEventosFecha(anno: Int, mes: Int, dia: Int){
        progressBar.visibility = View.VISIBLE
        //Aqui se llamaria a las funciones que se encargan de obtener los objetos de la BD
        adapter= Eventos_Calendario_Adapter(eventosArray)
        rv.adapter=adapter
        rv.layoutManager=LinearLayoutManager(this)
        progressBar.visibility = View.INVISIBLE
    }
}