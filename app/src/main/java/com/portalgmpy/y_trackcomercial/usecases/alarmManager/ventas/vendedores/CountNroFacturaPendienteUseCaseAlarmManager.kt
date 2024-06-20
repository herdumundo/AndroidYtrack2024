package com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject

class CountNroFacturaPendienteUseCaseAlarmManager @Inject constructor(
    private val repository: A0_YtvVentasRepository
)
{
    suspend fun Obtener(): Int {
        return repository.getNroFacturaPendienteCount()
    }
}