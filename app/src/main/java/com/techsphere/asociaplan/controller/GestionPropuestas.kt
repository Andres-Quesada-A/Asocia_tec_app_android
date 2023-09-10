package com.techsphere.asociaplan.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.models.Propuesta
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types
import java.time.ZoneId

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"
suspend fun getAllPropuestasBD(idUsuario: Int) : MutableList<Propuesta>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarPropuestas @inIdAsociacion=?, @inIdPropuesta=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idUsuario)
        cs.setNull(2, Types.INTEGER)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var propuestas = mutableListOf<Propuesta>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var propuesta = Propuesta(recordSets.getInt("id"),recordSets.getString("Tematica"),
                            recordSets.getString("Objetivos"),recordSets.getString("Actividades"),
                            recordSets.getString("Detalles"),recordSets.getString("NombreE"),
                            recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            propuestas.add(propuesta)
        }
        return propuestas
    }catch (ex: SQLException){
        Log.e("Error SQL Exception: ", ex.message.toString())
        return mutableListOf()
    }catch (ex1: ClassNotFoundException){
        Log.e("Error Class Not Found: ", ex1.message.toString())
        return mutableListOf()
    }catch (ex2: Exception) {
        Log.e("Error Exception: ", ex2.message.toString())
        return mutableListOf()
    }
}

suspend fun getBuscarPropuestaBD(idUsuario: Int, idPropuesta: Int) : MutableList<Propuesta>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarPropuestas @inIdAsociacion=?, @inIdPropuesta=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idUsuario)
        cs.setInt(2, idPropuesta)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var propuestas = mutableListOf<Propuesta>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var propuesta = Propuesta(recordSets.getInt("id"),recordSets.getString("Tematica"),
                recordSets.getString("Objetivos"),recordSets.getString("Actividades"),
                recordSets.getString("Detalles"),recordSets.getString("NombreE"),
                recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            propuestas.add(propuesta)
        }
        return propuestas
    }catch (ex: SQLException){
        Log.e("Error SQL Exception: ", ex.message.toString())
        return mutableListOf()
    }catch (ex1: ClassNotFoundException){
        Log.e("Error Class Not Found: ", ex1.message.toString())
        return mutableListOf()
    }catch (ex2: Exception) {
        Log.e("Error Exception: ", ex2.message.toString())
        return mutableListOf()
    }
}

suspend fun gestionarPropuestaDB(idPropuesta: Int,idEstado: Int) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call GestionPropuesta @inIdPropuesta=?, @inIdNombreTipo=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idPropuesta)
        cs.setInt(2, idEstado)
        cs.registerOutParameter(3, Types.INTEGER)
        cs.execute()
        // Creamos la lista que contendra las asociaciones
        var result = cs.getInt(3)
        Log.i("SP OutCode", "Resultado: "+result.toString())
        return result
    }catch (ex: SQLException){
        Log.e("Error SQL Exception: ", ex.message.toString())
        return 50001
    }catch (ex1: ClassNotFoundException){
        Log.e("Error Class Not Found: ", ex1.message.toString())
        return 50002
    }catch (ex2: Exception) {
        Log.e("Error Exception: ", ex2.message.toString())
        return 50003
    }
}