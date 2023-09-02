package com.techsphere.asociaplan.models

class Estudiantes (Nombre: String, Contacto: String, CodigoCarrera: String) {
    private var name : String
    private var contacto : String
    private var cod : String
    init {
        this.name=Nombre
        this.contacto=Contacto
        this.cod=CodigoCarrera
    }
    fun getNombre(): String{
        return name
    }
    fun getContacto(): String{
        return contacto
    }
    fun getCodigo(): String{
        return cod
    }
}