package com.techsphere.asociaplan.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Actividad (id: Int, inicio: LocalDate, fin: LocalDate, ubicacion: String, recursos: String,
                    nombre: String) {
    private var id: Int
    private var inicio: LocalDate
    private var fin: LocalDate
    private var ubicacion: String
    private var recursos: String
    private var nombre: String

    init {
        this.id = id
        this.inicio = inicio
        this.fin = fin
        this.ubicacion = ubicacion
        this.recursos = recursos
        this.nombre = nombre
    }

    fun getId(): Int{
        return id
    }
    fun getInicio(): LocalDate{
        return inicio
    }
    fun getFin(): LocalDate{
        return fin
    }
    fun getUbicacion(): String{
        return ubicacion
    }
    fun getRecursos(): String{
        return recursos
    }
    fun getNombre(): String{
        return nombre
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFechaFinToString(): String {
        var day = fin.dayOfMonth.toString()
        if (fin.dayOfMonth<10){
            day="0"+day
        }
        val month = fin.monthValue
        val year = fin.year
        return "${day}/${month}/${year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFechaToString(): String {
        var day = inicio.dayOfMonth.toString()
        if (inicio.dayOfMonth<10){
            day="0"+day
        }
        val month = inicio.monthValue
        val year = inicio.year
        return "${day}/${month}/${year}"
    }
}