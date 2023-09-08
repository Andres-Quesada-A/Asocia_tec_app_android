package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper
import com.techsphere.asociaplan.controller.AuthController

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            chechAuthentication()
        }, 3000)
    }

    private fun chechAuthentication(){
        val authHelper = AuthHelper(this)
        if (authHelper.isLogged()){
            val userType = authHelper.getAccountType()
            if (userType==1){
                val intent = Intent(this, menu_admin::class.java)
                startActivity(intent)
                finish()
            } else if (userType==2){
                val intent = Intent(this, menu_estudiante::class.java)
                startActivity(intent)
                finish()
            } else if (userType==3){
                val intent = Intent(this, menu_asociacion::class.java)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Tipo de cuenta desconocido. Se eliminara la cuenta",
                    Toast.LENGTH_LONG).show()
                authHelper.logoutAccount()
                finish()
            }
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}