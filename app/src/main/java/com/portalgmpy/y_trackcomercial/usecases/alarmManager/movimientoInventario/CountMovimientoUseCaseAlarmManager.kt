package com.portalgmpy.y_trackcomercial.usecases.alarmManager.movimientoInventario

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class CountMovimientoUseCaseAlarmManager  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend  fun CountPendientes(): Int {
        return movimientosRepository.getCount()
    }
}