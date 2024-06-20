package com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores

import com.portalgmpy.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject


class GetNroFacturaPendienteUseCaseAlarmManager @Inject constructor(
    private val repository: A0_YtvVentasRepository
)
{
    suspend fun Obtener(): nroFacturaPendiente {
        return repository.getNroFacturaPendiente()
    }
}