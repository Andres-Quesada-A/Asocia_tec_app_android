package com.techsphere.asociaplan.models

class Propuesta (id: Int, tematica: String, objetivos: String, actividades: String,
                    detalles: String, estado: String, promotor: String){
    private var id: Int
    private var tematica: String
    private var objetivos: String
    private var actividades: String
    private var detalles: String
    private var estado: String
    private var promotor: String

    init {
        this.id = id
        this.tematica = tematica
        this.objetivos = objetivos
        this.actividades = actividades
        this.detalles = detalles
        this.estado = estado
        this.promotor = promotor
    }

    fun getId(): Int{
        return id
    }
    fun getTematica(): String{
        return tematica
    }
    fun getObjetivos(): String{
        return objetivos
    }
    fun getActividades(): String{
        return actividades
    }
    fun getDetalles(): String{
        return detalles
    }
    fun getEstado(): String{
        return estado
    }
    fun getPromotor(): String{
        return promotor
    }

}