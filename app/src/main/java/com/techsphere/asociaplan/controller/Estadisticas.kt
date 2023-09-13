package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Estadisticas
import com.techsphere.asociaplan.models.Evaluacion
import com.techsphere.asociaplan.models.Tablas
import com.techsphere.asociaplan.view.estadisticas
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun getEstadisticasDB(idAsociacion: Int) : MutableList<Estadisticas>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call EstadisticasAsociacion @inIdAsociacion=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idAsociacion)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var estadisticas = mutableListOf<Estadisticas>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var estadistica = Estadisticas(recordSets.getString("Nombre"),recordSets.getInt("Promedio"),
                                            recordSets.getInt("CantdadEventos"),recordSets.getInt("CantidadActividades"),
                                            recordSets.getInt("InsEliminadas"),recordSets.getInt("InsConfirmadas"),
                                            recordSets.getInt("InsPendientes"),recordSets.getInt("Participantes"),
                                            recordSets.getInt("Espacios"),recordSets.getInt("Propuestas"))
            // Lo añadimos a la lista
            estadisticas.add(estadistica)
        }
        return estadisticas
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

suspend fun getComentariosPorEventoDB(idAsociacion: Int) : MutableList<Tablas>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call ComentariosPorEvento @inIdAsociacion=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idAsociacion)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var tablas = mutableListOf<Tablas>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var tabla = Tablas(recordSets.getInt("ComentarioEvento"))
            // Lo añadimos a la lista
            tablas.add(tabla)
        }
        return tablas
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

suspend fun getPromedioPorEventoDB(idAsociacion: Int) : MutableList<Tablas>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call PromedioEvaluacionEvento @inIdAsociacion=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idAsociacion)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var tablas = mutableListOf<Tablas>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var tabla = Tablas(recordSets.getInt("PromedioEvento"))
            // Lo añadimos a la lista
            tablas.add(tabla)
        }
        return tablas
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