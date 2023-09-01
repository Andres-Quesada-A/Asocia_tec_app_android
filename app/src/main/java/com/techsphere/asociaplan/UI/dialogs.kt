package com.techsphere.asociaplan.UI

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.techsphere.asociaplan.R

class dialogs (context: Context) {
    private val context: Context
    init {
        this.context=context
    }
    /*
    Esta funcion crea un dialogo con un spinner que
    indica que el app se encuentra cargando.
    Nos devuelve una instancia del dialogo.
     */

    fun showLoadingDialog(): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_loading,null)
        builder.setView(view)
        builder.setCancelable(false)
        // Devolvemos el dialogo, para que sea facil de mostrar y ocultar
        return builder.create()
    }
    fun shorErrorLogin(errorType: Int){
        if (errorType!=1){
            val builder = AlertDialog.Builder(context)
            when (errorType){
                50004 ->{
                    builder.setTitle("Error")
                    builder.setMessage("El usuario no se encuentra registrado en el sistema")
                }
                50001->{
                    builder.setTitle("Error")
                    builder.setMessage("Ocurrio un error al iniciar sesion\nPor favor intentelo mas tarde")
                }
                else ->{
                    // Aqui creamos un error de tipo desconocido
                    builder.setTitle("Error")
                    builder.setMessage("Ocurrio un error al conectarse al servidor\nPor favor intentelo mas tarde")
                }
            }
            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            builder.create().show()
        }
    }
    fun showErrorSignup(errorType: Int){
        if (errorType!=1){
            val builder = AlertDialog.Builder(context)
            when (errorType){
                50002->{
                    builder.setTitle("Error")
                    builder.setMessage("El correo electronico ya se encuentra registrado\nPor favor intente con un correo distinto")
                }
                50003 ->{
                    builder.setTitle("Error")
                    builder.setMessage("Ocurrio un error al registrar al usuario\nPor favor intentelo mas tarde")
                }
                else ->{
                    // Aqui creamos un error de tipo desconocido
                    builder.setTitle("Error")
                    builder.setMessage("Error desconocido\nPor favor intentelo mas tarde")
                }
            }
            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            builder.create().show()
        }
    }
    fun showSuccessSignup(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Registro")
        builder.setMessage("El usuario fue registrado exitosamente")
        builder.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener { dialog, id ->
                (context as Activity).finish()
            }
        )
        builder.setCancelable(false)
        builder.create().show()
    }
    fun showNoPermissions(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Esta aplicacion requiere de permisos para funcionar." +
                "\nSe va a cerrar el app.")
        builder.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener { dialog, id ->
                (context as Activity).finish()
            }
        )
        builder.setCancelable(false)
        builder.create().show()
    }
}
