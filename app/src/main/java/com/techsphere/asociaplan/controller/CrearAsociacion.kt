package com.techsphere.asociaplan.controller

import android.util.Log
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun registerAsocInBD(Nombre : String, Contacto : String, Codigo : String, Descripcion: String, Correo: String, Contrasena: String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call RegistrarAsociacion @inCorreo=?, @inContrasena=?, @inNombre=?,@inDescripcion=?,@inContacto=?,@inCodigo=?,@inMiembros=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Correo)
        cs.setString(2, Contrasena)
        cs.setString(3, Nombre)
        cs.setString(4, Descripcion)
        cs.setString(5, Contacto)
        cs.setString(6, Codigo)
        cs.setString(7, "")
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(8, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(8)
        Log.i("SP OutCode", "Resultado: "+result.toString())
        return result
    }catch (ex: SQLException){
        Log.e("Error SQL Exception: ", ex.message.toString())
        return 0
    }catch (ex1: ClassNotFoundException){
        Log.e("Error Class Not Found: ", ex1.message.toString())
        return 0
    }catch (ex2: Exception) {
        Log.e("Error Exception: ", ex2.message.toString())
        return 0
    }
}
suspend fun EditarAsocInBD(id: Int, Nombre : String, Contacto : String, Codigo : String, Descripcion: String, Correo: String, Contrasena: String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call ModificarAsociacion @inIdAsociacion=?, @inCorreo=?, @inContrasena=?, @inNombre=?,@inDescripcion=?,@inContacto=?,@inCodigo=?,@inMiembros=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, id)
        cs.setString(2, Correo)
        cs.setString(3, Contrasena)
        cs.setString(4, Nombre)
        cs.setString(5, Descripcion)
        cs.setString(6, Contacto)
        cs.setString(7, Codigo)
        cs.setString(8, "")
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(9, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(9)
        Log.i("SP OutCode", "Resultado: "+result.toString())
        return result
    }catch (ex: SQLException){
        Log.e("Error SQL Exception: ", ex.message.toString())
        return 0
    }catch (ex1: ClassNotFoundException){
        Log.e("Error Class Not Found: ", ex1.message.toString())
        return 0
    }catch (ex2: Exception) {
        Log.e("Error Exception: ", ex2.message.toString())
        return 0
    }
}