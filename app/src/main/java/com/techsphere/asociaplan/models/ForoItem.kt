package com.techsphere.asociaplan.models

class ForoItem (id: Int, Titulo: String, Cuerpo: String, autor: String="") {
    private var titulo : String
    private var cuerpo : String
    private var idmensaje : Int
    private var autor: String
    init {
        this.titulo=Titulo
        this.cuerpo=Cuerpo
        this.idmensaje=id
        this.autor=autor
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
    fun getAutor(): String{
        return autor
    }
}