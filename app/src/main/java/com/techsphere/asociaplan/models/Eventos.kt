package com.techsphere.asociaplan.models

import java.time.LocalDateTime

class Eventos (titulo: String, descripcion: String, fecha: LocalDateTime, lugar: String, duracion: Int,
               requisitos: String){
    private var titulo: String
    private var descripcion: String
    private var fecha: LocalDateTime
    private var lugar: String
    private var duracion: Int
    //private var requisitos: String

    init {
        this.titulo=titulo
        this.descripcion=descripcion
        this.fecha=fecha
        this.lugar=lugar
        this.duracion=duracion
    }

    fun getTitulo(): String {
        return titulo
    }

    fun getDescripcion(): String {
        return descripcion
    }

    fun getFecha(): LocalDateTime {
        return fecha
    }

    fun getLugar(): String {
        return lugar
    }

    fun getDuracion(): Int {
        return duracion
    }
}