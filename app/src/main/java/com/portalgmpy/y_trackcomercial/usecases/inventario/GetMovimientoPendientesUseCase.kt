package com.portalgmpy.y_trackcomercial.usecases.inventario

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class GetMovimientoPendientesUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {

    suspend  fun GetPendientes(): List<lotesDemovimientos> {
        return movimientosRepository.getAllLotesMovimientos()
    }
}