package com.techsphere.asociaplan.models

class ForoItem (id: Int, Titulo: String, Cuerpo: String) {
    private var titulo : String
    private var cuerpo : String
    private var idmensaje : Int
    init {
        this.titulo=Titulo
        this.cuerpo=Cuerpo
        this.idmensaje=id
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