package com.techsphere.asociaplan.models

class ForoRespuesta (id: Int, Cuerpo: String, idMensaje: Int) {
    private var Id : Int
    private var cuerpo : String
    private var idmensaje : Int
    init {
        this.Id=id
        this.cuerpo=Cuerpo
        this.idmensaje=idMensaje
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
}