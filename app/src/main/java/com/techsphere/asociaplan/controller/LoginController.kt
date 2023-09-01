package com.techsphere.asociaplan.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.techsphere.asociaplan.auth.AuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginController(context: Context) {
    private val authHelper = AuthHelper(context)
    private val admin = Administrador()
    private val context = context
    fun loginUser (email: String, pass: String){
        CoroutineScope(Dispatchers.IO).launch {
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
                    //context.startActivity(mainIntent)
                    //(context as Activity).finish()
                }else{
                    Toast.makeText(context, "No se registro", Toast.LENGTH_SHORT).show()
                    /*
                    withContext(Main) {
                        carga.dismiss()
                        dialog(context).shorErrorLogin(50001)
                    }
                     */
                }
            } else {
                /*
                carga.dismiss()
                dialog(context).shorErrorLogin(0)

                 */
            }
        }
    }
}