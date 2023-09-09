package com.techsphere.asociaplan.models

class Colaborador (id: Int, Nombre: String, Contacto: String, Descripcion: String) {
    private var name : String
    private var contacto : String
    private var descripcion : String
    private var id : Int
    init {
        this.name=Nombre
        this.contacto=Contacto
        this.descripcion=Descripcion
        this.id=id
    }
    fun getNombre(): String{
        return name
    }
    fun getContacto(): String{
        return contacto
    }
    fun getDescripcion(): String{
        return descripcion
    }
    fun getid(): Int{
        return id
    }
}