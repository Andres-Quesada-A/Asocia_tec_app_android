package com.techsphere.asociaplan.view

import android.accounts.Account
import android.accounts.AccountManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.techsphere.asociaplan.R
import com.techsphere.asociaplan.auth.AuthHelper

class SplashActivity : AppCompatActivity() {
    val helper = AuthHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({

        }, 1000)
        /*
        val am: AccountManager = AccountManager.get(this)
        val accounts: Array<out Account> = am.getAccountsByType("com.example.reservacubitec.accounttype.CUENTA")
        for (account: Account in accounts){
            Log.i("Cuenta",account.name)
        }

        Account("Test", "com.techsphere.asociaplan.account").also { account ->
            am.addAccountExplicitly(account, "a", null)
        }


         */
    }
}