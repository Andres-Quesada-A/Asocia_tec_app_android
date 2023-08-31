package com.techsphere.asociaplan.controller

import android.content.Context
import com.techsphere.asociaplan.auth.AuthHelper

class LoginController(context: Context) {
    private val authHelper = AuthHelper(context)
    fun loginUser (email: String, pass: String): Boolean{
        authHelper.registerAccount(email, pass, 1)
        return true
    }
}