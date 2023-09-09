package com.techsphere.asociaplan.models

class ForoRespuesta (Titulo: String, Cuerpo: String) {
    private var titulo : String
    private var cuerpo : String
    init {
        this.titulo=Titulo
        this.cuerpo=Cuerpo
    }
    fun getTitulo(): String{
        return titulo
    }
    fun getCuerpo(): String{
        return cuerpo
    }
}