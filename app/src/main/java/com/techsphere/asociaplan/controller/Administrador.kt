package com.techsphere.asociaplan.controller

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Administrador {
    private val connectionString = "jdbc:jtds:sqlserver://serverapp-ap.database.windows.net;databaseName=BDAppAP;"+
            "user=adminEvenAP;password=EventosAP1;ssl=require"

    suspend fun loginUser(context: Context, email: String, pass: String): Array<Int>{
        var connection : Connection? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connection = DriverManager.getConnection(connectionString)
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