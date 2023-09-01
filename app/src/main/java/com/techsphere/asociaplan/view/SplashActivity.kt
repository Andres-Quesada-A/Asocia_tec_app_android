package com.techsphere.asociaplan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.controller.AuthController

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            /*
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
             */
            val login = AuthController(this)
            login.loginUser("dylanmoya70@gmail.com","1")
        }, 1000)
    }
}