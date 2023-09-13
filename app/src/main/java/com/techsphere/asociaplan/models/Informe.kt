package com.techsphere.asociaplan.models

import java.time.LocalDate

class Informe (titulo: String, fecha: LocalDate, participantes: Int, capacidad: Int,
                calificacion: Int, conf: Int, elim: Int, pendi: Int){

    private var titulo: String
    private var fecha: LocalDate
    private var participantes: Int
    private var capacidad: Int
    private var calificacion: Int
    private var conf: Int
    private var elim: Int
    private var pendi: Int

    init {
        this.titulo = titulo
        this.fecha = fecha
        this.participantes = participantes
        this.capacidad = capacidad
        this.calificacion = calificacion
        this.conf = conf
        this.elim = elim
        this.pendi = pendi
    }

    fun getTitulo(): String {
        return titulo
    }

    fun getFecha(): LocalDate {
        return fecha
    }
    fun getParticipantes(): Int {
        return participantes
    }
    fun getCapacidad(): Int {
        return capacidad
    }
    fun getCalificacion(): Int {
        return calificacion
    }
    fun getConfirmadas(): Int {
        return conf
    }
    fun getEliminadas(): Int {
        return elim
    }
    fun getPendientes(): Int {
        return pendi
    }
}