package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Colaborador
import com.techsphere.asociaplan.models.Estudiantes
import com.techsphere.asociaplan.models.Eventos_Asociacion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun getAllColaboradoresBD() : MutableList<Colaborador>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarColaboradores @inNombre=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            cs.setNull(1, Types.VARCHAR)
            cs.registerOutParameter(2, Types.INTEGER)
            var recordSets = cs.executeQuery()
            // Creamos la lista que contendra los estudiantes
            var estudiantes = mutableListOf<Colaborador>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Colaborador(recordSets.getInt("id"), recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                    recordSets.getString("Descipcion"))
                // Lo añadimos a la lista
                estudiantes.add(estudiante)
            }
            /*
            */
            Log.i("SP OutCode", "Resultado: "+cs.getInt(2).toString())
            return estudiantes
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

    suspend fun getColaboradoresBusqueda(Nombre: String) : MutableList<Colaborador>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarColaboradores @inNombre=?, @outCodeResult=?}")
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
            // Creamos la lista que contendra los estudiantes
            var estudiantes = mutableListOf<Colaborador>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Colaborador(recordSets.getInt("id"), recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                    recordSets.getString("Descipcion"))
                // Lo añadimos a la lista
                estudiantes.add(estudiante)
            }
            /*
            */
            Log.i("SP OutCode", "Resultado: "+cs.getInt(2).toString())
            return estudiantes
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

suspend fun EditarColaboradorInBD(id: Int, Descripcion: String, Contacto : String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call ModiciarColaborador @inIdColaborador=?, @inDescripcion=?, @inContacto=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, id)
        cs.setString(2, Descripcion)
        cs.setString(3, Contacto)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(4, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(4)
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

fun deleteColaboradorBD(codigo: Int){
    CoroutineScope(Dispatchers.IO).launch {
        eliminarColaborador(codigo)
    }
}

suspend fun eliminarColaborador(codigo: Int) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call EliminarColaborador @inIdColaborador=?, @outCodeResult=?}")
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

suspend fun getColaboradoresEvents(idEvento: Int, nombre: String=""): MutableList<Colaborador>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarColaboradoresEvento @inIdEvento=?, @inNombre=?," +
                "@outCodeResult=?}")
        // Aqui revisamos si tenemos que pasarle un parametro nulo
        cs.setInt(1, idEvento)
        if (nombre.isBlank()||nombre.isEmpty()){
            cs.setNull(2, Types.VARCHAR)
        } else{
            cs.setString(2, nombre)
        }
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(3, Types.INTEGER)
        // Se ejecuta la query
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra los estudiantes
        var colaborador: Colaborador
        var colaboradores = mutableListOf<Colaborador>()
        while (recordSets.next()){
            colaborador = Colaborador(recordSets.getInt("id"), recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                recordSets.getString("Descipcion"))
            colaboradores.add(colaborador)
        }
        Log.i("SP OutCode", "Resultado: "+cs.getInt(3).toString())
        return colaboradores
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
suspend fun getColaboradoresActividad(idEvento: Int, nombre: String=""): MutableList<Colaborador>{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarColaboradoresActividad @inIdActividad=?, @inNombre=?," +
                "@outCodeResult=?}")
        // Aqui revisamos si tenemos que pasarle un parametro nulo
        cs.setInt(1, idEvento)
        if (nombre.isBlank()||nombre.isEmpty()){
            cs.setNull(2, Types.VARCHAR)
        } else{
            cs.setString(2, nombre)
        }
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(3, Types.INTEGER)
        // Se ejecuta la query
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra los estudiantes
        var colaborador: Colaborador
        var colaboradores = mutableListOf<Colaborador>()
        while (recordSets.next()){
            colaborador = Colaborador(recordSets.getInt("id"), recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                recordSets.getString("Descipcion"))
            colaboradores.add(colaborador)
        }
        Log.i("SP OutCode", "Resultado: "+cs.getInt(3).toString())
        return colaboradores
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
suspend fun getCorreosColaboradoresEvento(Nombre: String, idEvento: Int) : String{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call BuscarColaboradoresEvento @inIdEvento=?, @inNombre=?, @outCodeResult=?}")
        // Aqui revisamos si tenemos que pasarle un parametro nulo
        cs.setInt(1, idEvento)
        if (Nombre.isBlank()){
            cs.setNull(2, Types.VARCHAR)
        } else{
            cs.setString(2, Nombre)
        }
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(3, Types.INTEGER)
        // Se ejecuta la query
        var recordSets = cs.executeQuery()
        // Creamos la lista que contendra los estudiantes
        var correos = ""
        while (recordSets.next()){
            correos = correos + ", "+ recordSets.getString("Correro")
        }
        Log.i("SP OutCode", "Resultado: "+cs.getInt(2).toString())
        return correos
    }catch (ex: SQLException){
        Log.e("Error SQL Exception: ", ex.message.toString())
        return ""
    }catch (ex1: ClassNotFoundException){
        Log.e("Error Class Not Found: ", ex1.message.toString())
        return ""
    }catch (ex2: Exception) {
        Log.e("Error Exception: ", ex2.message.toString())
        return ""
    }
}

suspend fun gestionColaborador(idAsociacion: Int, idEvento: Int, idActividad: Int,
                                tipoGestion: Int, idColaborador: Int): Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call GestionColaborador @inIdAsociacion=?, @inIdEvento=?, " +
                                    "@inIdActividad=?, @inTipoGestion=?, @inIdEstudiante=?," +
                                    "@outCodeResult=?}")
        // Asumimos que se nos pasa un valor no vacio
        cs.setInt(1, idAsociacion)
        cs.setInt(2, idEvento)
        cs.setInt(3, idActividad)
        cs.setInt(4, tipoGestion)
        cs.setInt(5, idColaborador)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(6, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        Log.i("SP OutCode", "Resultado: "+cs.getInt(6).toString())
        return cs.getInt(6)
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