package com.techsphere.asociaplan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.controller.AuthController
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var emailText: EditText
    private lateinit var carnetText: EditText
    private lateinit var areaEstudioText: EditText
    private lateinit var passText: EditText
    private lateinit var confirmPassText: EditText
    private lateinit var registerButton: Button
    private lateinit var authCtrl : AuthController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        nameText = findViewById(R.id.input_nombre)
        emailText = findViewById(R.id.input_correo)
        carnetText = findViewById(R.id.input_Carne)
        areaEstudioText = findViewById(R.id.input_area_estudio)
        passText = findViewById(R.id.input_contrasena)
        confirmPassText = findViewById(R.id.input_confirmacion_contrasena)
        registerButton = findViewById(R.id.button_registrar)
        registerButton.setOnClickListener {
            signUpUser()
        }
        authCtrl = AuthController(this)
    }

    fun signUpUser(){
        var name = nameText.text.toString()
        var email = emailText.text.toString()
        var carnet = carnetText.text.toString()
        var areaEstudio = areaEstudioText.text.toString()
        var password = passText.text.toString()
        var confirmPass = confirmPassText.text.toString()
        var isNombreEmpty = false
        var isCorreoEmpty = false
        var isCarnetEmpty = false
        var isAreaEmpty = false
        var isPassEmpty = false
        var isPassConfEmpty = false
        var isValidEmail = true
        if(name.isEmpty()||name.isBlank()){
            isNombreEmpty = true
        }
        if (carnet.isEmpty()||carnet.isBlank()){
            isCarnetEmpty = true
        }
        if (areaEstudio.isEmpty()||areaEstudio.isBlank()){
            isAreaEmpty = true
        }
        if (password.isEmpty()||password.isBlank()){
            isPassEmpty = true
        }
        if (confirmPass.isEmpty()||confirmPass.isBlank()){
            isPassConfEmpty = true
        }
        if (email.isEmpty()||email.isBlank()){
            isCorreoEmpty=true
        } else {
            var emailSplited = email.split("@")
            if (emailSplited.size==2){
                if (!emailSplited[1].equals("estudiantec.cr")){
                    isValidEmail = false
                }
            } else {
                isValidEmail = false
            }
        }
        if (!isNombreEmpty && !isCorreoEmpty && !isCarnetEmpty && !isAreaEmpty
            && !isPassEmpty && !isPassConfEmpty && isValidEmail){
            if (!password.equals(confirmPass)){
                confirmPassText.error="Las contraseñas deben de coincidir"
                return
            }
            if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$",
                    password)){
                passText.error="La contraseña debe de contener una letra mayuscula, una minuscula," +
                        " un numero y un caracter especial. Ademas debe contener 8 caracteres"
                return
            }
            authCtrl.registerStudent(name, email, carnet, areaEstudio, password)
        } else{
            if (isNombreEmpty){
                nameText.error="Rellene este campo"
            }
            if (isCorreoEmpty){
                emailText.error="Rellene este campo"
            }
            if (isCarnetEmpty){
                carnetText.error="Rellene este campo"
            }
            if (isAreaEmpty){
                areaEstudioText.error="Rellene este campo"
            }
            if (isPassEmpty){
                passText.error="Rellene este campo"
            }
            if (isPassConfEmpty){
                confirmPassText.error="Rellene este campo"
            }
            if (!isValidEmail){
                emailText.error="Solo se admiten correos del dominio @estudiantec.cr"
            }
        }

    }
}