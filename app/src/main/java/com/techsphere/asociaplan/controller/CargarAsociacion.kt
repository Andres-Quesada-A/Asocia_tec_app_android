package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Asociacion
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun getAllAsociacionesBD() : MutableList<Asociacion>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarAsociacion @inNombre=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setNull(1, Types.VARCHAR)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var asociaciones = mutableListOf<Asociacion>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var asociacion = Asociacion(recordSets.getInt("id"),recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                recordSets.getString("CodigoCarrera"), recordSets.getString("Descripcion"),recordSets.getString("Correo"))
            // Lo añadimos a la lista
            asociaciones.add(asociacion)
        }
        return asociaciones
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

suspend fun getAsociacionesBusqueda(Nombre: String) : MutableList<Asociacion>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarAsociacion @inNombre=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Nombre)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var asociaciones = mutableListOf<Asociacion>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var asociacion = Asociacion(recordSets.getInt("id"),recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                recordSets.getString("CodigoCarrera"), recordSets.getString("Descripcion"),recordSets.getString("Correo"))
            // Lo añadimos a la lista
            asociaciones.add(asociacion)
        }
        return asociaciones
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