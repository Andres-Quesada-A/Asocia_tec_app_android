package com.techsphere.asociaplan.models

class ForoRespuesta (id: Int, Cuerpo: String, idMensaje: Int, autor: String="") {
    private var Id : Int
    private var cuerpo : String
    private var idmensaje : Int
    private var autor: String

    init {
        this.Id=id
        this.cuerpo=Cuerpo
        this.idmensaje=idMensaje
        this.autor=autor
    }
    fun getid(): Int{
        return Id
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