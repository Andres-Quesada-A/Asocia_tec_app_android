package com.techsphere.asociaplan.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.models.Actividad
import com.techsphere.asociaplan.utils.EmailSender
import com.techsphere.asociaplan.view.actividades
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.sql.*
import java.util.Date


private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

@RequiresApi(Build.VERSION_CODES.O)
suspend fun getAllActividadesBD(idEvento: Int) : MutableList<Actividad>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarActividad @inNombre=?, @inIdEvento=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setNull(1, Types.VARCHAR)
        cs.setInt(2, idEvento)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var actividades = mutableListOf<Actividad>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var actividad = Actividad(recordSets.getInt("id"),
                recordSets.getDate("FechaInicio").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                recordSets.getDate("FechaFin").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                recordSets.getString("Ubicacion"),recordSets.getString("Recursos"), recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            actividades.add(actividad)
        }
        return actividades
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
suspend fun getActividadesBusqueda(Nombre: String, idEvento: Int ) : MutableList<Actividad>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarActividad @inNombre=?, @inIdEvento=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Nombre)
        cs.setInt(2, idEvento)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var actividades = mutableListOf<Actividad>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var actividad = Actividad(recordSets.getInt("id"),
                recordSets.getDate("FechaInicio").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                recordSets.getDate("FechaFin").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                recordSets.getString("Ubicacion"),recordSets.getString("Recursos"), recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            actividades.add(actividad)
        }
        return actividades
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
suspend fun getActividadIdBusqueda(idActividad: Int ) : MutableList<Actividad>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarActividadId @inIdActividad=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idActividad)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var actividades = mutableListOf<Actividad>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var actividad = Actividad(recordSets.getInt("id"),
                recordSets.getDate("FechaInicio").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                recordSets.getDate("FechaFin").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                recordSets.getString("Ubicacion"),recordSets.getString("Recursos"), recordSets.getString("Nombre"))
            // Lo añadimos a la lista
            actividades.add(actividad)
        }
        return actividades
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

fun eliminarActividadDBAux(idActividad: Int ){
    CoroutineScope(Dispatchers.IO).launch {
        eliminarActividadDB(idActividad)
    }
}
suspend fun eliminarActividadDB(idActividad: Int ): Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call EliminarAtividad @inIdActividad=?, @outCodeResult=?}")
        // Asumimos que se nos pasa un valor no vacio
        cs.setInt(1, idActividad)
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

suspend fun registrarActividad(idEvento: Int, inicio: java.sql.Date, fin: java.sql.Date, ubicacion: String,
                               recursos: String, nombre: String): Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call RegistrarActividad @inIdEvento=?, @inInicio=?," +
                "@inFin=?, @inUbicacion=?, @inRecursos=?," +
                "@inNombre=?, @outCodeResult=?}")
        // Asumimos que se nos pasa un valor no vacio
        cs.setInt(1, idEvento)
        cs.setDate(2, inicio)
        cs.setDate(3, fin)
        cs.setString(4, ubicacion)
        cs.setString(5, recursos)
        cs.setString(6, nombre)
        Log.e("Id: ",idEvento.toString())
        Log.e("Nombre: ",nombre)
        Log.e("Fecha: ",inicio.toString())
        Log.e("Des: ",recursos)
        Log.e("Lug: ",ubicacion)
        Log.e("Dur: ",fin.toString())
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(7, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        Log.i("SP OutCode", "Resultado: "+cs.getInt(7).toString())
        return cs.getInt(7)
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

suspend fun editarActividad(idActividad: Int, inicio: java.sql.Date, fin: java.sql.Date, ubicacion: String,
                               recursos: String, nombre: String): Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call ModificarActividad @inIdActividad=?, @inInicio=?," +
                "@inFin=?, @inUbicacion=?, @inRecursos=?," +
                "@inNombre=?, @outCodeResult=?}")
        // Asumimos que se nos pasa un valor no vacio
        cs.setInt(1, idActividad)
        cs.setDate(2, inicio)
        cs.setDate(3, fin)
        cs.setString(4, ubicacion)
        cs.setString(5, recursos)
        cs.setString(6, nombre)
        Log.e("Id: ",idActividad.toString())
        Log.e("Nombre: ",nombre)
        Log.e("Fecha: ",inicio.toString())
        Log.e("Des: ",recursos)
        Log.e("Lug: ",ubicacion)
        Log.e("Dur: ",fin.toString())
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(7, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        Log.i("SP OutCode", "Resultado: "+cs.getInt(7).toString())
        return cs.getInt(7)
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
