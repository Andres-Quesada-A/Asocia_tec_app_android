package com.techsphere.asociaplan.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Clase que se encarga de las funciones de autenticacion
class AuthController(context: Context) {
    private val authHelper = AuthHelper(context)
    private val admin = Administrador()
    private val context = context
    fun loginUser (email: String, pass: String){
        val carga = dialogs(context).showLoadingDialog()
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                carga.show()
            }
            val res = admin.loginUser(email, pass)
            if (res.isNotEmpty()){
                val userType : Int = res[0]
                var resultAccount = authHelper.registerAccount(email, pass, userType)
                if (resultAccount) {
                    val userType = authHelper.getAccountType()
                    var mainIntent : Intent
                    if(userType==1){
                        //mainIntent = Intent(context, AdminActivity::class.java)
                    } else {
                        //mainIntent = Intent(context, UserActivity::class.java)
                    }
                    withContext(Main){
                        carga.dismiss()
                    }
                    //context.startActivity(mainIntent)
                    //(context as Activity).finish()
                }else{
                    Toast.makeText(context, "No se registro", Toast.LENGTH_SHORT).show()

                    withContext(Main) {
                        carga.dismiss()
                        dialogs(context).shorErrorLogin(50001)
                    }
                }
            } else {
                withContext(Main){
                    carga.dismiss()
                    dialogs(context).shorErrorLogin(0)
                }
            }
        }
    }

    fun registerStudent (name:String, email: String, carne: String, area: String,
                         pass: String){
        val carga = dialogs(context).showLoadingDialog()
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Main){
                carga.show()
            }
            val res = admin.registerStudent(name, email, carne, area, pass)
            if (res == 1){
                /*
                EmailSender().sendEmailRegister(correo)
                 */
                val successDiaglog = dialogs(context)
                withContext(Main){
                    carga.dismiss()
                    successDiaglog.showSuccessSignup()
                }
            } else {
                withContext(Main){
                    carga.dismiss()
                    dialogs(context).shorErrorLogin(0)
                }
            }
        }
    }
}