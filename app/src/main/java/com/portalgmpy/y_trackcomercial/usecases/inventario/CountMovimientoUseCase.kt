package com.portalgmpy.y_trackcomercial.usecases.inventario

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class CountMovimientoUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend  fun CountPendientes(): Int {
        return movimientosRepository.getCount()
    }
}