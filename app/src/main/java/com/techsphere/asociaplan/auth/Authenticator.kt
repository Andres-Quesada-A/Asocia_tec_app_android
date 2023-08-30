package com.techsphere.asociaplan.auth

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.content.Context
import android.os.Bundle
import android.util.Log

/*
La documentacion en la pagina de android dice que se tiene que implementar el codigo de
las funciones. Como no tenemos tiempo y como en el proyecto anterior no hubo problema
al implementarlo asi, lo dejare asi.
 */

class Authenticator(context: Context) : AbstractAccountAuthenticator(context) {
    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle {
        TODO("Not yet implemented")
    }

    override fun addAccount(
        p0: AccountAuthenticatorResponse?,
        p1: String?,
        p2: String?,
        p3: Array<out String>?,
        p4: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
        Log.i("Authenticator","Entro al authenticador")
    }

    override fun confirmCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun getAuthToken(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun getAuthTokenLabel(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun updateCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun hasFeatures(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Array<out String>?
    ): Bundle {
        TODO("Not yet implemented")
    }
}