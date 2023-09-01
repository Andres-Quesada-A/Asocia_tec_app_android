package com.techsphere.asociaplan.controller

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types

class Administrador {
    private val connectionString = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
            "user=adminEvenAP;password=EventosAP1;ssl=require"

    suspend fun loginUser(email: String, pass: String): Array<Int>{
        var connection : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
            var sp = connection.prepareCall("{call LoginUser @inEmail=?, @inPassword=?, @outUserType=?, @outCodeResult=?}")
            sp.setString(1, email)
            sp.setString(2, pass)
            sp.registerOutParameter(3, Types.INTEGER)
            sp.registerOutParameter(4, Types.INTEGER)
            sp.execute()
            Log.i("Resultado Login", "${sp.getInt(4)}")
            return arrayOf<Int>()
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
}