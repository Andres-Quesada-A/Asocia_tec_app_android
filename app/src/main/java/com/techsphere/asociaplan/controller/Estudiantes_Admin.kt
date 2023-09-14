package com.techsphere.asociaplan.controller

import android.util.Log
import com.techsphere.asociaplan.models.Estudiantes_Admin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.*

private const val connectionString : String = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
        "user=adminEvenAP;password=EventosAP1;ssl=require"

suspend fun getAllEstudiantesBD() : MutableList<Estudiantes_Admin>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarEstudiante @inNombre=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            cs.setNull(1, Types.VARCHAR)
            cs.registerOutParameter(2, Types.INTEGER)
            var recordSets = cs.executeQuery()
            // Creamos la lista que contendra los estudiantes
            var estudiantes = mutableListOf<Estudiantes_Admin>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Estudiantes_Admin(recordSets.getInt("id"), recordSets.getString("Correro"),recordSets.getString("Contrasena"), recordSets.getString("AreaEstudio"), recordSets.getString("Nombre"),
                    recordSets.getInt("Carne"))
                // Lo añadimos a la lista
                estudiantes.add(estudiante)
            }
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

    suspend fun getEstudiantesBusqueda(Nombre: String) : MutableList<Estudiantes_Admin>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarEstudiante @inNombre=?, @outCodeResult=?}")
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
            var estudiantes = mutableListOf<Estudiantes_Admin>()
            while (recordSets.next()){
                // Creamos un nuevo estudiante
                var estudiante = Estudiantes_Admin(recordSets.getInt("id"), recordSets.getString("Correro"),recordSets.getString("Contrasena"), recordSets.getString("AreaEstudio"), recordSets.getString("Nombre"),
                    recordSets.getInt("Carne"))
                // Lo añadimos a la lista
                estudiantes.add(estudiante)
            }
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

suspend fun EditarEstudianteInBD(id: Int, nombre: String, correo : String,carne: Int, area: String, contrasena : String
) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call ModificarEstudiante @inIdEstudiante=?, @inNombre=?,@inCorreo=?,@inCarne=?, @inArea=?,@inContrasena=?,@outCodeResult=?}")
        // Asumimos que se nos pasan valores no nulos
        cs.setInt(1, id)
        cs.setString(2, nombre)
        cs.setString(3, correo)
        cs.setInt(4, carne)
        cs.setString(5, area)
        cs.setString(6, contrasena)
        // Le indicamos el parametro de salida y su tipo
        cs.registerOutParameter(7, Types.INTEGER)
        // Se ejecuta la query
        cs.execute()
        var result = cs.getInt(7)
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

fun deleteEstudianteBD(codigo: Int){
    CoroutineScope(Dispatchers.IO).launch {
        eliminarEstudiante(codigo)
    }
}

suspend fun eliminarEstudiante(codigo: Int) : Int{
    var conn : Connection? = null
    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(connectionString)
        var cs = conn.prepareCall("{call EliminarEstudiante @inIdEstudiante=?, @outCodeResult=?}")
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