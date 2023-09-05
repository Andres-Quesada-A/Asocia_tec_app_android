package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.controller.AuthController

class LoginActivity : AppCompatActivity() {
    private lateinit var emailText: EditText
    private lateinit var passText: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)
        emailText=findViewById(R.id.txt_correo)
        passText=findViewById(R.id.txt_contrasena)
        loginBtn=findViewById(R.id.button_iniciar_sesion)
        signUpBtn=findViewById(R.id.button_registrarse)
        loginBtn.setOnClickListener {
            loginUser()
        }
        signUpBtn.setOnClickListener {
            signUpUser()
        }
    }
    fun loginUser(){
        val email= emailText.text.toString()
        val pass= passText.text.toString()
        val authCtrl = AuthController(this)
        var emptyEmail = false
        var emptyPassword = false
        if (email.isEmpty()|| email.isBlank()){
            emptyEmail = true
        }
        if (pass.isEmpty() || pass.isBlank()){
            emptyPassword = true
        }
        if (!emptyEmail && !emptyPassword){
            authCtrl.loginUser(email,pass)
        } else{
            if (emptyPassword){
                passText.error="Rellene este campo"
            }
            if (emptyEmail){
                emailText.error="Rellene este campo"
            }
        }
    }

    fun signUpUser(){
        // No se finaliza la actividad actual para que cuando se termine de registrar
        // el usuario vuelva rapidamente al login
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}