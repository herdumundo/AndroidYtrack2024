package com.portalgmpy.y_trackcomercial.usecases.inventario

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class EnviarMovimientoPendientesUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend fun enviarPendientes(lotes: EnviarLotesDeMovimientosRequest) {
        return movimientosRepository.exportarMovimientos(lotes)
    }
}