package com.portalgmpy.y_trackcomercial.usecases.informeInventario

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class GetInformeUseCase @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    // suspend
    suspend  fun getInforme(fecha: String): MutableList<com.portalgmpy.y_trackcomercial.data.model.models.InformeInventario> {
        return movimientosRepository.getInformeInventario(fecha)
    }
}