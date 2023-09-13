package com.techsphere.asociaplan.models

class Evaluacion (comentario: String, calificaion: Int) {

    private var comentario: String
    private var calificaion: Int

    init {
        this.comentario = comentario
        this.calificaion = calificaion
    }

    fun getComentario(): String{
        return comentario
    }
    fun getCalificacion(): Int{
        return  calificaion
    }
}