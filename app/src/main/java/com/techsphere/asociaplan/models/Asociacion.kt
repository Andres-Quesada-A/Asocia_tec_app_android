package com.techsphere.asociaplan.models

class Asociacion (id: Int, Nombre: String, Contacto: String, CodigoCarrera: String, Descripcion: String, Correo: String) {
    private var id : Int
    private var name : String
    private var contacto : String
    private var cod : String
    private var descripcion : String
    private var correo: String
    init {
        this.id=id
        this.name=Nombre
        this.contacto=Contacto
        this.cod=CodigoCarrera
        this.descripcion=Descripcion
        this.correo=Correo
    }
    fun getId(): Int{
        return id
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
    fun getCorreo(): String{
        return correo
    }
}