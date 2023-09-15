package com.techsphere.asociaplan.UI

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.Administrador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                50005 ->{
                    builder.setTitle("Error")
                    builder.setMessage("La contraseña ingresada es incorrecta")
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
    fun showSuccessDialog(type: Int, finish: Boolean=true){
        val builder = AlertDialog.Builder(context)
        // Es un numero arbitrario, luego se puede cambiar
        when (type){
            17 -> {
                builder.setTitle("Inscripcion a eventos")
                builder.setMessage("Se ha inscrito al evento de forma exitosa")
            }
            18 ->{
                builder.setTitle("Notificaciones de eventos")
                builder.setMessage("Se ha suscrito a las notificaciones de forma exitosa")
            }
            20 ->{
                builder.setTitle("Confirmacion de asistencia")
                builder.setMessage("Se confirmo la asistencia al evento de manera exitosa")
            }
            21 ->{
                builder.setTitle("Cancelar inscripcion")
                builder.setMessage("Se cancelo la inscripcion al evento en forma exitosa")
            }
            22 ->{
                builder.setTitle("Gestionar capacidad")
                builder.setMessage("Se modifico el numero maximo de inscripciones para el evento en " +
                        "forma exitosa")
            }
            23 ->{
                builder.setTitle("Editar evento")
                builder.setMessage("El evento se modifico de forma exitosa")
            }
            28 ->{
                builder.setTitle("Añadir colaborador")
                builder.setMessage("Se añadio el colaborador de forma exitosa")
            }
            29 ->{
                builder.setTitle("Eliminar colaborador")
                builder.setMessage("Se elimino el colaborador de forma exitosa")
            }
            else -> {
                builder.setTitle("Exito")
                builder.setMessage("La accion se llevo a cabo de manera exitosa")
            }
        }
        if (finish){
            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    (context as Activity).finish()
                }
            )
        } else{
            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                }
            )
        }
        builder.setCancelable(false)
        builder.create().show()
    }
    fun showErrorDialog(type: Int, finish: Boolean=true){
        val builder = AlertDialog.Builder(context)
        // Es un numero arbitrario, luego se puede cambiar
        when (type){
            16 -> {
                builder.setTitle("Inscripcion a eventos")
                builder.setMessage("No se pudo inscribir al evento\n" +
                        "No hay cupo disponible")
            }
            17 -> {
                builder.setTitle("Inscripcion a eventos")
                builder.setMessage("No se pudo inscribir al evento\n" +
                        "Por favor, intentelo mas tarde")
            }
            18 ->{
                builder.setTitle("Notificaciones de eventos")
                builder.setMessage("No se pudo inscribir a las notificaciones\n" +
                        "Por favor, intentelo mas tarde")
            }
            20 ->{
                builder.setTitle("Confirmacion de asistencia")
                builder.setMessage("No se pudo confirmar la asistencia al evento\n" +
                        "Por favor, intentelo mas tarde")
            }
            21 ->{
                builder.setTitle("Cancelar inscripcion")
                builder.setMessage("No se pudo cancelar la inscripcion al evento\n" +
                        "Por favor, intentelo mas tarde")
            }
            22 ->{
                builder.setTitle("Gestionar capacidad")
                builder.setMessage("No se pudo modificar el numero maximo de inscripciones para el evento.\n" +
                        "Por favor, intentelo mas tarde")
            }
            23 ->{
                builder.setTitle("Editar evento")
                builder.setMessage("No se pudo modificar los datos del evento.\n" +
                        "Por favor, intentelo mas tarde")
            }
            28 ->{
                builder.setTitle("Añadir colaborador")
                builder.setMessage("No se pudo añadir el colaborador al evento\n" +
                        "Por favor, intentelo mas tarde")
            }
            29 ->{
                builder.setTitle("Eliminar colaborador")
                builder.setMessage("No se pudo eliminar el colaborador al evento\n" +
                        "Por favor, intentelo mas tarde")
            }
            else -> {
                builder.setTitle("Error")
                builder.setMessage("Ocurrio un error inesperado\n" +
                        "Por favor, intentelo mas tarde")
            }
        }

        if (finish){
            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    (context as Activity).finish()
                }
            )
        } else{
            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                }
            )
        }
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
    fun showSpecialStoragePermissions(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Permiso requerido")
        builder.setMessage("Esta funcion requiere acceder al almacenamiento del dispositivo." +
                        "\nPara poder activar el permiso busca el nombre del app, y activa el permiso")
        builder.setPositiveButton("Activar permiso",
            DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                (context as Activity).startActivity(intent)
            }
        )
        builder.setCancelable(false)
        builder.create().show()
    }
    fun showNotificationEventDialog(idEvento: Int): AlertDialog{
        val dialog = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_notificar_evento, null)
        dialog.setView(view)
        dialog.setCancelable(true)
        val btnAceptar = view.findViewById<Button>(R.id.aceptarButton)
        val btnCancelar = view.findViewById<Button>(R.id.cancelarButton)
        val d = dialog.create()
        btnAceptar.setOnClickListener {
            d.dismiss()
            val load = showLoadingDialog()
            val userId = AuthHelper(context).getAccountId()
            load.show()
            CoroutineScope(Dispatchers.IO).launch {
                val res = Administrador().registerInterest(idEvento,userId)
                withContext(Dispatchers.Main){
                    load.dismiss()
                    if (res==1){
                        showSuccessDialog(18, false)
                    } else{
                        showErrorDialog(18, false)
                    }
                }
            }
        }
        btnCancelar.setOnClickListener {
            d.dismiss()
        }
        return d

    }
}
