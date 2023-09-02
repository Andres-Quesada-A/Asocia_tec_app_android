package com.techsphere.asociaplan.controller

import android.util.Log
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun registerAsocInBD(Nombre : String, Contacto : String, Codigo : String, Descripcion: String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call RegistrarAsociacion @inCodigo=?, @inEstado=?, @inCantidadTiempo=?, @inFechaInicio=?, " +
                "@inFechaFin=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Nombre)
        cs.setString(2, Contacto)
        cs.setString(3, Codigo)
        cs.setString(4, Descripcion)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(6, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(6)
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
