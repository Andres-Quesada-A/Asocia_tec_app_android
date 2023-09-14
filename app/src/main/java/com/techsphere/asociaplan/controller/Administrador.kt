package com.techsphere.asociaplan.controller

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.techsphere.asociaplan.models.Eventos
import com.techsphere.asociaplan.models.Eventos_Asociacion
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
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getInscripcionesEstudiante(id: Int, nombre: String=""): MutableList<Eventos>{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call BuscarEventoInscrito @inIdEstudiante=?, @inNombre=?," +
                    "@outCodeResult=?}")
            sp.setInt(1, id)
            if (nombre.isEmpty()||nombre.isBlank()){
                sp.setNull(2,Types.VARCHAR)
            } else{
                sp.setString(2, nombre)
            }
            sp.registerOutParameter(3, Types.INTEGER)
            var rSet = sp.executeQuery()
            var eventos = mutableListOf<Eventos>()
            while (rSet.next()){
                var evento = Eventos(rSet.getInt("id"), rSet.getString("Titulo"), rSet.getString("Descripcion"),
                    rSet.getDate("Fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    rSet.getString("Lugar"), rSet.getInt("Duracion"), rSet.getString("Requisitos"),
                    rSet.getString("Categoria"), rSet.getInt("Estado"))
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
    suspend fun getAllEventosAsociation(id:Int, nombre: String="") : MutableList<Eventos>{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var sp = conn.prepareCall("{call BuscarEventoGestion @inNombre=?,@inIdAsociacion=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            if (nombre.isEmpty()||nombre.isBlank()){
                sp.setNull(1,Types.VARCHAR)
            } else{
                sp.setString(1, nombre)
            }
            sp.setInt(2, id)
            sp.registerOutParameter(3, Types.INTEGER)
            var rSet = sp.executeQuery()
            // Creamos la lista que contendra las asociaciones
            var eventos = mutableListOf<Eventos>()
            while (rSet.next()){
                // Creamos una nuevo evento
                var evento = Eventos(rSet.getInt("id"), rSet.getString("Titulo"), rSet.getString("Descripcion"),
                    rSet.getDate("Fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    rSet.getString("Lugar"), rSet.getInt("Duracion"), rSet.getString("Requisitos"),
                    rSet.getString("Categoria"))
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
    suspend fun manageCapacity(idAsociacion: Int, idEvento: Int, numParticipantes: Int): Int{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call GestionEvento @inIdAsociacion=?, @inIdEvento=?," +
                    "@inParticipantes=?, @outCodeResult=?}")
            sp.setInt(1, idAsociacion)
            sp.setInt(2, idEvento)
            sp.setInt(3, numParticipantes)
            sp.registerOutParameter(4, Types.INTEGER)
            val rs = sp.execute()
            Log.i("SP Result", "${sp.getInt(4)}")
            return sp.getInt(4)
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
    suspend fun confirmAssistance(idEstudiante: Int, idEvento: Int): Int{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call ConfirmarInscripcion @inIdEstudiante=?, @inIdEvento=?," +
                    "@outCodeResult=?}")
            sp.setInt(1, idEstudiante)
            sp.setInt(2, idEvento)
            sp.registerOutParameter(3, Types.INTEGER)
            val rs = sp.executeQuery()
            while (rs.next()){

            }
            Log.i("SP Result", "${sp.getInt(3)}")
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
    suspend fun cancelAssistance(idEstudiante: Int, idEvento: Int): Int{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call EliminarInscripcion @inIdEstudiante=?, @inIdEvento=?," +
                    "@outCodeResult=?}")
            sp.setInt(1, idEstudiante)
            sp.setInt(2, idEvento)
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

    suspend fun registerInterest(idEvento: Int, idEstudiante: Int): Int{
        var connection : Connection? = null
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            val sp = connection.prepareCall("{call RecordatorioEvento @inIdEvento=?, @inIdEstudiante=?," +
                    "@outCodeResult=?}")
            sp.setInt(1, idEvento)
            sp.setInt(2, idEstudiante)
            sp.registerOutParameter(3, Types.INTEGER)
            sp.execute()
            Log.i("SP Result", "${sp.getInt(3)}")
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
    suspend fun getInteresadosEvento(idEvento: Int): MutableList<String>{
        var conn : Connection? = null
        try {
            Log.i("Aqui", "Dento del sp que obtiene los correos")
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var sp = conn.prepareCall("{call BuscarInteresadosEvento @inIdEvento=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            sp.setInt(1, idEvento)
            sp.registerOutParameter(2, Types.INTEGER)
            var rSet = sp.executeQuery()
            // Creamos la lista que contendra los correos
            var correos = mutableListOf<String>()
            while (rSet.next()){
                // Creamos una nuevo evento
                var correo = rSet.getString("Correro")
                // Lo añadimos a la lista
                correos.add(correo)
            }
            Log.i("SP Result","${sp.getInt(2)}")
            return correos
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

    suspend fun ConsultarCapacidadEvento(idEvento: Int): Int{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarCapacidadEvento @inIdEvento=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            cs.setInt(1, idEvento)
            cs.registerOutParameter(2, Types.INTEGER)
            var recordSet = cs.executeQuery()
            var campos = 1
            while (recordSet.next()){
                // Creamos una nueva asociacion
                campos = recordSet.getInt("Campos")
            }
            return campos
        }catch (ex: SQLException){
            Log.e("Error SQL Exception: ", ex.message.toString())
            return 1
        }catch (ex1: ClassNotFoundException){
            Log.e("Error Class Not Found: ", ex1.message.toString())
            return 1
        }catch (ex2: Exception) {
            Log.e("Error Exception: ", ex2.message.toString())
            return 1
        }
    }
    suspend fun getAsociationEmail(idEvento: Int): String{
        var conn : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            conn = DriverManager.getConnection(connectionString)
            var cs = conn.prepareCall("{call BuscarCorreoAsociacionEvento @inIdEvento=?, @outCodeResult=?}")
            // Asumimos que se nos pasan valores no nulos
            cs.setInt(1, idEvento)
            cs.registerOutParameter(2, Types.INTEGER)
            var recordSet = cs.executeQuery()
            var correo = ""
            while (recordSet.next()){
                // Creamos una nueva asociacion
                correo = recordSet.getString("Correo")
            }
            return correo
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
}