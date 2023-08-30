package com.techsphere.asociaplan

import android.accounts.Account
import android.accounts.AccountManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val am: AccountManager = AccountManager.get(this)
        val accounts: Array<out Account> = am.getAccountsByType("com.example.reservacubitec.accounttype.CUENTA")
        for (account: Account in accounts){
            Log.i("Cuenta",account.name)
        }

        Account("Test", "com.techsphere.asociaplan.account").also { account ->
            am.addAccountExplicitly(account, "a", null)
        }

    }
}