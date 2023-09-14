package com.techsphere.asociaplan.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.view.menu
import com.techsphere.asociaplan.view.menu_admin
import com.techsphere.asociaplan.view.menu_asociacion
import com.techsphere.asociaplan.view.menu_estudiante
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
                val result = res[1]
                if (result==1){
                    val userType : Int = res[0]
                    val userId: Int = res[2]
                    Log.i("Tipo Usuario", "${userType}")
                    var resultAccount = authHelper.registerAccount(email, pass, userType, userId)
                    if (resultAccount) {
                        val userType = authHelper.getAccountType()
                        Log.i("Tipo Usuario en if", "${userType}")
                        var mainIntent : Intent
                        if(userType==1){
                            mainIntent = Intent(context, menu_admin::class.java)
                        } else if (userType==2){
                            mainIntent = Intent(context, menu_estudiante::class.java)
                        } else{
                            mainIntent = Intent(context, menu_asociacion::class.java)
                        }
                        withContext(Main){
                            carga.dismiss()
                        }
                        context.startActivity(mainIntent)
                        (context as Activity).finish()
                    }else{
                        withContext(Main) {
                            carga.dismiss()
                            dialogs(context).shorErrorLogin(50001)
                        }
                    }
                } else if (result==50004){
                    withContext(Main){
                        carga.dismiss()
                        dialogs(context).shorErrorLogin(50004)
                    }
                } else if (result==50005){
                    withContext(Main){
                        carga.dismiss()
                        dialogs(context).shorErrorLogin(50005)
                    }
                } else{
                    withContext(Main){
                        carga.dismiss()
                        dialogs(context).shorErrorLogin(0)
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
                //(context as Activity).finish()
            }
        }
    }

}