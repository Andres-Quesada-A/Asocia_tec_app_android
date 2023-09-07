package com.techsphere.asociaplan.models

class Miembro (id: Int, Nombre: String, Carne: String, idAsociacion: Int) {
    private var name : String
    private var carne : String
    private var id : Int
    private var idAsociacion: Int
    init {
        this.name=Nombre
        this.carne=Carne
        this.id=id
        this.idAsociacion=idAsociacion
    }
    fun getNombre(): String{
        return name
    }
    fun getCarne(): String{
        return carne
    }
    fun getid(): Int{
        return id
    }
    fun getidAsociacion(): Int{
        return idAsociacion
    }
}