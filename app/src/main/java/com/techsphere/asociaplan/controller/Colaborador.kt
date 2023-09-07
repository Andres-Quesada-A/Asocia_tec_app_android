package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Asociacion
import com.techsphere.asociaplan.models.Estudiantes
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun AgregarColaboradorBD(Codigo: String) : MutableList<Estudiantes> {
    var conn: Connection? = null
    var estudiantes = mutableListOf<Estudiantes>()
    return estudiantes
}
suspend fun getAllColaboradoresBD() : MutableList<Estudiantes>{
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
            var estudiantes = mutableListOf<Estudiantes>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Estudiantes(recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                    recordSets.getString("CodigoCarrera"))
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

    suspend fun getColaboradoresBusqueda(Nombre: String) : MutableList<Estudiantes>{
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
            var estudiantes = mutableListOf<Estudiantes>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Estudiantes(recordSets.getString("Nombre"), recordSets.getString("Contacto"),
                    recordSets.getString("CodigoCarrera"))
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