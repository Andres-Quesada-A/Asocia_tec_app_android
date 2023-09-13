package com.techsphere.asociaplan.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.models.Evaluacion
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.models.Informe
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.sql.Date


private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"
@RequiresApi(Build.VERSION_CODES.O)
suspend fun getAllAventosEvaluarBD(idUsuario: Int) : MutableList<Eventos>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarEventoInscrito @inIdEstudiante=?, @inNombre=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idUsuario)
        cs.setNull(2, Types.VARCHAR)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var eventos = mutableListOf<Eventos>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var evento = Eventos(recordSets.getInt("id"),recordSets.getString("Titulo"),"",
                recordSets.getDate("Fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),"",0,"","")
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
suspend fun getEventosEvaluarBusqueda(idUsuario: Int, Nombre: String) : MutableList<Eventos>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarEventoInscrito @inIdEstudiante=?, @inNombre=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idUsuario)
        cs.setString(2, Nombre)
        cs.registerOutParameter(3, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var eventos = mutableListOf<Eventos>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var evento = Eventos(recordSets.getInt("id"),recordSets.getString("Titulo"),"",
                recordSets.getDate("Fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),"",0,"","")
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

fun EnviarEvaluacionDB(Comentario : String, Calificaion : Int, IdEven : Int, IdUsu: Int
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call AgregarCalificacion @inComentario=?, @inCalificacion=?," +
                " @inIdEstudiante=?,@inIdEvento=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setString(1, Comentario)
        cs.setInt(2, Calificaion)
        cs.setInt(3, IdUsu)
        cs.setInt(4, IdEven)
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

@RequiresApi(Build.VERSION_CODES.O)
suspend fun getInformeEventoDB(idEvento: Int) : MutableList<Informe>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call InformeEventoAsociacion @inIdEvento=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idEvento)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var informes = mutableListOf<Informe>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var informe = Informe(recordSets.getString("Titulo"),recordSets.getDate("Fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            recordSets.getInt("Participantes"), recordSets.getInt("Capacidad"),
                            recordSets.getInt("Promedio"),recordSets.getInt("Confirmadas"),
                            recordSets.getInt("Eliminadas"),recordSets.getInt("Pendientes"))
            // Lo añadimos a la lista
            informes.add(informe)
        }
        return informes
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

suspend fun getEvaluacionesEventoDB(idEvento: Int) : MutableList<Evaluacion>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarEvaluacionEvento @inIdEvento=?, @outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idEvento)
        cs.registerOutParameter(2, Types.INTEGER)
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra las asociaciones
        var evaluaciones = mutableListOf<Evaluacion>()
        while (recordSets.next()){
            // Creamos una nueva asociacion )
            var evaluacion = Evaluacion(recordSets.getString("Comentario"),recordSets.getInt("Calificacion"))
            // Lo añadimos a la lista
            evaluaciones.add(evaluacion)
        }
        return evaluaciones
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