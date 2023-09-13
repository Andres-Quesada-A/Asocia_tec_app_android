package com.techsphere.asociaplan.models

class Tablas (cantidad: Int) {
    private var cantidad: Int
    init {
        this.cantidad = cantidad
    }
    fun getCantidad(): Int{
        return cantidad
    }
}