package com.techsphere.asociaplan.models

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

class Eventos (id: Int, titulo: String, descripcion: String, fecha: LocalDate, lugar: String, duracion: Int,
               requisitos: String, categoria: String): Serializable {
    private var id: Int
    private var titulo: String
    private var descripcion: String
    private var fecha: LocalDate
    private var lugar: String
    private var duracion: Int
    private var requisitos: String
    private var categoria: String

    init {
        this.id=id
        this.titulo=titulo
        this.descripcion=descripcion
        this.fecha=fecha
        this.lugar=lugar
        this.duracion=duracion
        this.requisitos=requisitos
        this.categoria=categoria
    }

    fun getId(): Int{
        return id
    }

    fun getTitulo(): String {
        return titulo
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

    fun getCategoria(): String{
        return categoria
    }
    fun getRequisitos(): String{
        return requisitos
    }
}