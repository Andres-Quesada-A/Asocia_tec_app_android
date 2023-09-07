package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Estudiantes
import com.techsphere.asociaplan.models.Miembro
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun AgregarMiembroBD(idUsuario : Int, idAsoc : Int) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call AgregarMiembro @inIdAsociacion=?, @inIdUsuario=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, idAsoc)
        cs.setInt(2, idUsuario)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(3, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(3)
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
suspend fun getAllEstudiantesBD(Id:Int) : MutableList<Miembro>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarEstudianteAsociacion @inIdAsociacion=?, @inNombre=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            cs.setInt(1, Id)
            cs.setNull(2, Types.VARCHAR)
            cs.registerOutParameter(3, Types.INTEGER)
            var recordSets = cs.executeQuery()
            // Creamos la lista que contendra los estudiantes
            var estudiantes = mutableListOf<Miembro>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Miembro(recordSets.getInt("id"),recordSets.getString("Nombre"), recordSets.getString("Carne"), Id)
                // Lo añadimos a la lista
                estudiantes.add(estudiante)
            }
            /*
            */
            Log.i("SP OutCode", "Resultado: "+cs.getInt(4).toString())
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

    suspend fun getEstudiantesBusqueda(Id:Int, Nombre: String) : MutableList<Miembro>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarEstudianteAsociacion @inIdAsociacion=?, @inNombre=?, @outCodeResult=?}")
            // Aqui revisamos si tenemos que pasarle un parametro nulo
            cs.setInt(1, Id)
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
            var estudiantes = mutableListOf<Miembro>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Miembro(recordSets.getInt("id"),recordSets.getString("Nombre"), recordSets.getString("Carne"), Id)
                // Lo añadimos a la lista
                estudiantes.add(estudiante)
            }
            /*
            */
            Log.i("SP OutCode", "Resultado: "+cs.getInt(4).toString())
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