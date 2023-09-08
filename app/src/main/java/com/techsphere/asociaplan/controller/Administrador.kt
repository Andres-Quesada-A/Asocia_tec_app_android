package com.techsphere.asociaplan.controller

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.models.Eventos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types
import java.time.ZoneId

class Administrador {
    private val connectionString = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
            "user=adminEvenAP;password=EventosAP1;ssl=require"

    suspend fun loginUser(email: String, pass: String): Array<Int>{
        var connection : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            var sp = connection.prepareCall("{call LoginUser @inEmail=?, @inPassword=?, @outUserType=?," +
                    "@outCodeResult=?, @outId=?}")
            sp.setString(1, email)
            sp.setString(2, pass)
            sp.registerOutParameter(3, Types.INTEGER)
            sp.registerOutParameter(4, Types.INTEGER)
            sp.registerOutParameter(5, Types.INTEGER)
            sp.execute()
            Log.i("Resultado Login", "${sp.getInt(4)}")
            return arrayOf(sp.getInt(3), sp.getInt(4), sp.getInt(5))
        }catch (ex: SQLException){
            Log.e("Error SQL Exception: ", ex.message.toString())
            return arrayOf<Int>()
        }catch (ex1: ClassNotFoundException){
            Log.e("Error Class Not Found: ", ex1.message.toString())
            return arrayOf<Int>()
        }catch (ex2: Exception) {
            Log.e("Error Exception: ", ex2.message.toString())
            return arrayOf<Int>()
        }
    }

    suspend fun registerStudent(name:String, email: String, carne: String, area: String,
                                pass: String): Int{
        var connection : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            var sp = connection.prepareCall("{call RegistrarEstudiante @inNombre=?, @inCorreo=?, " +
                    "@inCarne=?, @inArea=?, @inContrasena=?, @outCodeResult=?}")
            sp.setString(1, name)
            sp.setString(2, email)
            sp.setString(3, carne)
            sp.setString(4, area)
            sp.setString(5, pass)
            sp.registerOutParameter(6, Types.INTEGER)
            sp.execute()
            Log.i("Resultado Register", "${sp.getInt(6)}")
            return sp.getInt(6)
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
    //Luego veo donde puedo mover esto
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEventosFecha(fecha: Date): MutableList<Eventos>{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call BuscarEventoFecha @inFecha=?, @outCodeResult=?}")
            sp.setDate(1, fecha)
            sp.registerOutParameter(2, Types.INTEGER)
            var rSet = sp.executeQuery()
            var eventos = mutableListOf<Eventos>()
            while (rSet.next()){
                var evento = Eventos(rSet.getInt("id"), rSet.getString("Titulo"), rSet.getString("Descripcion"),
                rSet.getDate("Fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),rSet.getString("Lugar"),
                rSet.getInt("Duracion"), rSet.getString("Requisitos"), rSet.getString("Categoria"))
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

    suspend fun inscribirEstudianteEvento(idEvento: Int, idEstudiante: Int): Int{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call InscribirEvento @inIdEvento=?, @inIdEstudiante=?," +
                    "@outCodeResult=?}")
            sp.setInt(1, idEvento)
            sp.setInt(2, idEstudiante)
            sp.registerOutParameter(3, Types.INTEGER)
            sp.execute()
            return sp.getInt(3)
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

}