package com.techsphere.asociaplan.models

class Estudiantes_Admin (id: Int,Correo:String,Contrasena:String,AreaEstudio:String, Nombre: String, Carne: Int) {
    private var name : String
    private var correo : String
    private var contrasena : String
    private var carne : Int
    private var area : String
    private var id : Int
    init {
        this.name=Nombre
        this.correo=Correo
        this.contrasena=Contrasena
        this.carne=Carne
        this.area=AreaEstudio
        this.id=id
    }
    fun getNombre(): String{
        return name
    }
    fun getCorreo(): String{
        return correo
    }
    fun getContrasena(): String{
        return contrasena
    }
    fun getCarne(): Int{
        return carne
    }
    fun getArea(): String{
        return area
    }
    fun getid(): Int{
        return id
    }
}