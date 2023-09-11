package com.techsphere.asociaplan.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.UI.dialogs
import com.techsphere.asociaplan.auth.AuthHelper

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val permissionGranted = checkPermission()
        if (!permissionGranted){
            requestPermissions()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                chechAuthentication()
            }, 3000)
        }
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

    private fun checkPermission(): Boolean{
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            200
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 200) {
            if (grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        chechAuthentication()
                    }, 3000)
                } else {
                    dialogs(this).showNoPermissions()
                }
            }
        }
    }

}