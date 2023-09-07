package com.techsphere.asociaplan.models

class Asociacion (id: Int, Nombre: String, Contacto: String, CodigoCarrera: String, Descripcion: String, Correo: String, Contrasena: String) {
    private var id : Int
    private var name : String
    private var contacto : String
    private var cod : String
    private var descripcion : String
    private var correo: String
    private var contrasena: String
    init {
        this.id=id
        this.name=Nombre
        this.contacto=Contacto
        this.cod=CodigoCarrera
        this.descripcion=Descripcion
        this.correo=Correo
        this.contrasena=Contrasena
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
    fun getContrasena(): String{
        return contrasena
    }
}