package com.portalgmpy.y_trackcomercial.usecases.alarmManager.movimientoInventario

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class GetMovimientoPendientesUseCaseAlarmManager  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {

    suspend  fun GetPendientes(): List<lotesDemovimientos> {
        return movimientosRepository.getAllLotesMovimientos()
    }
}