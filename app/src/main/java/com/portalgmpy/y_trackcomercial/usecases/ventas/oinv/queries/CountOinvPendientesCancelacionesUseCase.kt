package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class CountOinvPendientesCancelacionesUseCase @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    suspend  fun CountPendientes(): Int {
        return OinvRepository.getAllCountPendientesExportarCancelaciones()
    }
}