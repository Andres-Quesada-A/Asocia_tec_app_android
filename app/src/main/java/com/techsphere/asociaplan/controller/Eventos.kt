package com.techsphere.asociaplan.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.models.Eventos_Asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.*
import java.time.ZoneId

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

@RequiresApi(Build.VERSION_CODES.O)
suspend fun getAllEventosBD(id:Int) : MutableList<Eventos_Asociacion>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarEventoaAsociacion @inNombre=?,@inIdAsociacion=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setNull(1, Types.VARCHAR)
        cs.setInt(2, id)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var eventos = mutableListOf<Eventos_Asociacion>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var evento = Eventos_Asociacion(recordSets.getInt("id"),recordSets.getString("Titulo"),recordSets.getString("Descripcion"), recordSets.getDate("Fecha").toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate(),
                recordSets.getString("Lugar"), recordSets.getInt("Duracion"),recordSets.getString("Requisitos"),recordSets.getString("Nombre"),recordSets.getString("Capacidad"),recordSets.getString("Disponibilidad"))
            // Lo añadimos a la lista
            eventos.add(evento)
        }
        return eventos
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
@RequiresApi(Build.VERSION_CODES.O)
suspend fun getEventosBusqueda(Nombre: String, id: Int) : MutableList<Eventos_Asociacion>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarEventoaAsociacion @inNombre=?,@inIdAsociacion=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Nombre)
        cs.setInt(2, id)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var eventos = mutableListOf<Eventos_Asociacion>()
        while (recordSets.next()){
            // Creamos una nueva asociacion
            var evento = Eventos_Asociacion(recordSets.getInt("id"),recordSets.getString("Titulo"),recordSets.getString("Descripcion"), recordSets.getDate("Fecha").toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate(),
                recordSets.getString("Lugar"), recordSets.getInt("Duracion"),recordSets.getString("Requisitos"),recordSets.getString("Nombre"),recordSets.getString("Capacidad"),recordSets.getString("Disponibilidad"))
            // Lo añadimos a la lista
            eventos.add(evento)
        }
        return eventos
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

suspend fun registerEventoInBD(Id:Int,Nombre:String,Fecha:Date,Descripcion:String,Lugar:String,Duracion:Int,Requerimientos:String,Categoria:String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call RegistrarEvento @inIdAsociacion=?, @inTitulo=?, @inFecha=?,@inDescripcion=?, @inLugar=?, @inDuracion=?,@inRequisitos=?, @inCategoria=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, Id)
        cs.setString(2, Nombre)
        cs.setDate(3, Fecha)
        cs.setString(4, Descripcion)
        cs.setString(5, Lugar)
        cs.setInt(6, Duracion)
        cs.setString(7, Requerimientos)
        cs.setString(8, Categoria)
        Log.e("Id: ",Id.toString())
        Log.e("Nombre: ",Nombre)
        Log.e("Fecha: ",Fecha.toString())
        Log.e("Des: ",Descripcion)
        Log.e("Lug: ",Lugar)
        Log.e("Dur: ",Duracion.toString())
        Log.e("Req: ",Requerimientos)
        Log.e("Cat: ",Categoria)
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

fun deleteEventoBD(codigo: Int){
    CoroutineScope(Dispatchers.IO).launch {
        eliminarEvento(codigo)
    }
}

suspend fun eliminarEvento(codigo: Int) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call EliminarEvento @inIdEvento=?, @outCodeResult=?}")
        // Asumimos que se nos pasa un valor no vacio
        cs.setInt(1, codigo)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(2, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        Log.i("SP OutCode", "Resultado: "+cs.getInt(2).toString())
        return cs.getInt(2)
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

suspend fun EditarEventoInBD(Id:Int,IdEvento:Int, Nombre:String,Fecha:Date,Descripcion:String,Lugar:String,Duracion:Int,Requerimientos:String,Categoria:String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call ModificarEvento @inIdAsociacion=?, @inIdEvento=?, @inTituloNuevo=?, @inFecha=?, @inDescripcion=?, @inLugar=?, @inDuracion=?, @inRequisitos=?, @inCategoria=?, @outCodeResult=?}")

// Asumimos que se nos pasan valores no nulos
        cs.setInt(1, Id)
        cs.setInt(2, IdEvento)
        cs.setString(3, Nombre)
        cs.setDate(4, Fecha)
        cs.setString(5, Descripcion)
        cs.setString(6, Lugar)
        cs.setInt(7, Duracion)
        cs.setString(8, Requerimientos)
        cs.setString(9, Categoria)

// Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(10, Types.INTEGER)
        cs.execute()

        var result = cs.getInt(10)
        Log.i("SP OutCode", "Resultado: " + result.toString())
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