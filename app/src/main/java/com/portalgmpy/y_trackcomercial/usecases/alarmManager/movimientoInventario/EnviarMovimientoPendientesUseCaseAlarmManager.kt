package com.portalgmpy.y_trackcomercial.usecases.alarmManager.movimientoInventario

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class EnviarMovimientoPendientesUseCaseAlarmManager  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend fun enviarPendientes(lotes: EnviarLotesDeMovimientosRequest) {
        return movimientosRepository.exportarMovimientos(lotes)
    }
}