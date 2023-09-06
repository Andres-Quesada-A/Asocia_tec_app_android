package com.techsphere.asociaplan.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class AuthHelper(context: Context) {
    // Por el momento lo dejo asi, si ocurre algun problema lo eliminare.
    private val accMan = AccountManager.get(context)
    // Unicamente lo usamos para los toast
    private val cont = context
    fun registerAccount (email: String, pass: String, userType: Int): Boolean{
        val numAcc = accMan.getAccountsByType("com.techsphere.asociaplan.account").size
        if (numAcc==0){
            // Creamos la cuenta con el correo
            var userAccount =  Account(email,"com.techsphere.asociaplan.account")
            // Añadimos la cuenta al administrador y le añadimos la contraseña
            //Segun la documentacion debe de ser un token y no la contraseña
            //en texto plano, pero no hay tiempo para cifrarla
            val success = accMan.addAccountExplicitly(userAccount, pass, null)
            if (success){
                var userRole: String
                //Revisamos el rol del usuario para almacenarlo
                if (userType==1){
                    userRole="Admin"
                } else if (userType==2){
                    userRole="Estudiante"
                } else if (userType==3){
                    userRole="Asociacion"
                } else{
                    userRole="Desconocido"
                }
                // Añadimos el rol a la cuenta
                accMan.setUserData(userAccount, "Role", userRole)
                return true
            } else{
                return false
            }
        } else {
            return false
        }
    }
    fun isLogged(): Boolean{
        // Obtenemos el numero de cuentas registradas
        val numAccounts = accMan.getAccountsByType("com.techsphere.asociaplan.account").size
        return numAccounts==1
    }
    fun getAccountType(): Int{
        //Obtenemos todas las cuentas registradas
        val accounts = accMan.getAccountsByType("com.techsphere.asociaplan.account")
        //Si solo hay una
        if (accounts.size==1){
            // Obtenemos el rol del usuario almacenado en el dispositivo
            val userRole = accMan.getUserData(accounts[0], "Role")
            // Revisamos que no este vacio
            if (userRole.isNotEmpty()&&userRole.isNotBlank()){
                if (userRole=="Admin"){
                    return 1
                }  else if (userRole=="Estudiante"){
                    return 2
                } else if (userRole=="Asociacion"){
                    return 3
                } else{
                    return 0
                }
            }
        }
        return 0
    }

    fun getAccountEmail(): String?{
        // Se obtienen las cuentas registradas
        val accounts = accMan.getAccountsByType("com.techsphere.asociaplan.account")
        // Si solo hay una
        if (accounts.size==1){
            // Revisamos que tenga un email
            if (accounts[0].name.isNotBlank()&&accounts[0].name.isNotEmpty()){
                // Devolvemos el email del usuario
                return accounts[0].name
            }
        }
        return null
    }

    fun logoutAccount() : Boolean{
        val accounts = accMan.getAccountsByType("com.techsphere.asociaplan.account")
        // Se asume que solo existe una cuenta registrada en el telefono
        if (accounts.size==1){
            val res = accMan.removeAccount(accounts[0], cont as Activity, null, null)
            // Esperamos mientras se elimina la cuenta del telefono
            while (!res.isDone){
                Log.i("Logout", "Cerrando sesion")
            }
            // Revisamos si se termino de remover la cuenta
            return !isLogged()
        }
        return true
    }
}