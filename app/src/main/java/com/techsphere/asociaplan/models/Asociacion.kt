package com.techsphere.asociaplan.models

class Asociacion (name: String, Contacto: String, CodigoCarrera: String, Descripcion: String) {
    private var name : String
    private var contacto : String
    private var cod : String
    private var descripcion : String
    init {
        this.name=name
        this.contacto=Contacto
        this.cod=CodigoCarrera
        this.descripcion=Descripcion
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
    fun getDescripcion(): String{
        return descripcion
    }
}