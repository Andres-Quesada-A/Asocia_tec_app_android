package com.techsphere.asociaplan.models

class ForoItem (Titulo: String, Cuerpo: String, idMensaje: Int) {
    private var titulo : String
    private var cuerpo : String
    private var idmensaje : Int
    init {
        this.titulo=Titulo
        this.cuerpo=Cuerpo
        this.idmensaje=idMensaje
    }
    fun getTitulo(): String{
        return titulo
    }
    fun getCuerpo(): String{
        return cuerpo
    }
    fun getidMensaje(): Int{
        return idmensaje
    }
}