package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.ForoItem
import com.techsphere.asociaplan.models.ForoRespuesta
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun getAllForoItemsBD() : MutableList<ForoItem>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call TomarComentarios @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.registerOutParameter(1, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var items = mutableListOf<ForoItem>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var item = ForoItem(recordSets.getInt("id"),recordSets.getString("Titulo"),
                recordSets.getString("Cuerpo"), recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            items.add(item)
        }
        return items
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

suspend fun getForoItemsBD(id : Int) : MutableList<ForoItem>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call TomarComentarioId @idMensaje=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, id)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var items = mutableListOf<ForoItem>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var item = ForoItem(recordSets.getInt("id"),recordSets.getString("Titulo"),
                recordSets.getString("Cuerpo"), recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            items.add(item)
        }
        return items
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

suspend fun getAllForoRespuestasBD(id : Int) : MutableList<ForoRespuesta>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call TomarRespeustas @idMensaje=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, id)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var Respuestas = mutableListOf<ForoRespuesta>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var respuesta = ForoRespuesta(recordSets.getInt("id"), recordSets.getString("Cuerpo"),
                recordSets.getInt("idMensaje"), recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            Respuestas.add(respuesta)
        }
        return Respuestas
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

suspend fun RedactarForoInBD(Titulo : String, Cuerpo : String, IdEstudiante: Int
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call AgregarMensaje @inTitulo=?, @inCuerpo=?, @inIdMensaje=?,@inIdEstudiante=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Titulo)
        cs.setString(2, Cuerpo)
        cs.setNull(3, Types.INTEGER)
        cs.setInt(4, IdEstudiante)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(5, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(5)
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

suspend fun RedactarRespuestaForoInBD(Titulo : String, Cuerpo : String, Idmensaje : Int, IdEstudiante: Int
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call AgregarMensaje @inTitulo=?, @inCuerpo=?, @inIdMensaje=?,@inIdEstudiante=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Titulo)
        cs.setString(2, Cuerpo)
        cs.setInt(3, Idmensaje)
        cs.setInt(4, IdEstudiante)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(5, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(5)
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