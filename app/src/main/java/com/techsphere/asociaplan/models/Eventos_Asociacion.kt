package com.techsphere.asociaplan.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

class Eventos_Asociacion (id: Int, titulo: String, descripcion: String, fecha: LocalDate, lugar: String, duracion: Int,
                          requisitos: String, nombre: String, capacidad: String, disponibilidad: String): Serializable {
    private var id: Int
    private var titulo: String
    private var nombre: String
    private var disponibilidad: String
    private var descripcion: String
    private var fecha: LocalDate
    private var lugar: String
    private var duracion: Int
    private var capacidad: String
    private var requisitos: String

    init {
        this.id=id
        this.titulo=titulo
        this.descripcion=descripcion
        this.fecha=fecha
        this.lugar=lugar
        this.duracion=duracion
        this.requisitos=requisitos
        this.nombre=nombre
        this.disponibilidad=disponibilidad
        this.capacidad=capacidad
    }
    fun getCapacidad(): String{
        return capacidad
    }
    fun getId(): Int{
        return id
    }
    fun getCategoria(): String {
        return nombre
    }
    fun getDisponibilidad(): String {
        return disponibilidad
    }

    fun getTitulo(): String {
        return titulo
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFechaToString(): String {
        var day = fecha.dayOfMonth.toString()
        if (fecha.dayOfMonth<10){
            day="0"+day
        }
        val month = fecha.monthValue
        val year = fecha.year
        return "${day}/${month}/${year}"
    }

    fun getDescripcion(): String {
        return descripcion
    }

    fun getFecha(): LocalDate {
        return fecha
    }

    fun getLugar(): String {
        return lugar
    }

    fun getDuracion(): Int {
        return duracion
    }

    fun getRequisitos(): String{
        return requisitos
    }
}