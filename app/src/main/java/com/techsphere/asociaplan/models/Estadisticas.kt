package com.techsphere.asociaplan.models

class Estadisticas (nombre: String, promedio: Int, eventos: Int, actividades: Int,
                    eliminadas: Int, confirmadas: Int, pendientes: Int,
                    participantes: Int, espacios: Int, propuestas: Int) {

    private var nombre: String
    private var promedio: Int
    private var eventos: Int
    private var actividades: Int
    private var eliminadas: Int
    private var confirmadas: Int
    private var pendientes: Int
    private var participantes: Int
    private var espacios: Int
    private var propuestas: Int

    init {
        this.nombre = nombre
        this.promedio = promedio
        this.eventos = eventos
        this.actividades = actividades
        this.eliminadas = eliminadas
        this.confirmadas = confirmadas
        this.pendientes = pendientes
        this.participantes = participantes
        this.espacios = espacios
        this.propuestas = propuestas
    }
    fun getNombre(): String{
        return nombre
    }
    fun getPromedio(): Int{
        return promedio
    }
    fun getEventos(): Int{
        return eventos
    }
    fun getActividades(): Int{
        return actividades
    }
    fun getEliminadas(): Int{
        return eliminadas
    }
    fun getConfirmadas(): Int{
        return confirmadas
    }
    fun getPendientes(): Int{
        return pendientes
    }
    fun getParticipantes(): Int{
        return participantes
    }
    fun getEspacios(): Int{
        return espacios
    }
    fun getPropuestas(): Int{
        return propuestas
    }
}