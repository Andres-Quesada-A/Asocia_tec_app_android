package com.techsphere.asociaplan.controller

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

fun EnviarPropuestaDB(Tematica : String, Objetivos : String, Actividades : String, Detalles: String,
                      idAsociacion: Int,  idUsuario: Int
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call AgregarPropuesta @inTematia=?, @inObjetivos=?," +
                " @inActividades=?,@inDetalles=?,@inIdEstudiante=?,@inIdAsociacion=? ,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Tematica)
        cs.setString(2, Objetivos)
        cs.setString(3, Actividades)
        cs.setString(4, Detalles)
        cs.setInt(5, idUsuario)
        cs.setInt(6, idAsociacion)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(7, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(7)
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