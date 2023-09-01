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
        var cs = conn.prepareCall("{call BuscarAsociacion @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setNull(1, Types.VARCHAR)
        cs.setNull(2, Types.VARCHAR)
        cs.setNull(3, Types.VARCHAR)
        cs.setNull(4, Types.VARCHAR)
        cs.registerOutParameter(4, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra los cubiculos
        var asociaciones = mutableListOf<Asociacion>()
        while (recordSets.next()){
            // Creamos un nuevo cubiculo
            var asociacion = Asociacion(recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                recordSets.getString("CodigoCarrera"), recordSets.getString("Descripcion"))
            // Lo añadimos a la lista
            asociaciones.add(asociacion)
            Log.i("SP Result", "El codigo del cubiculo es: "+recordSets.getString("Codigo"))
        }
        /*
        */
        Log.i("SP OutCode", "Resultado: "+cs.getInt(4).toString())
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
        var cs = conn.prepareCall("{call BuscarCubiculosEstudiante @inNombre=?, @outCodeResult=?}")
        // Aqui revisamos si tenemos que pasarle un parametro nulo
        if (Nombre.isBlank()){
            cs.setNull(1, Types.VARCHAR)
        } else{
            cs.setString(1, Nombre)
        }
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(2, Types.INTEGER)
        // Se ejecuta la query
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra los cubiculos
        var asociaciones = mutableListOf<Asociacion>()
        while (recordSets.next()){
            // Creamos un nuevo cubiculo
            var asociacion = Asociacion(recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                recordSets.getString("CodigoCarrera"), recordSets.getString("Descripcion"))
            // Lo añadimos a la lista
            asociaciones.add(asociacion)
            Log.i("SP Result", "El codigo del cubiculo es: "+recordSets.getString("Codigo"))
        }
        Log.i("SP OutCode", "Resultado: "+cs.getInt(4).toString())
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