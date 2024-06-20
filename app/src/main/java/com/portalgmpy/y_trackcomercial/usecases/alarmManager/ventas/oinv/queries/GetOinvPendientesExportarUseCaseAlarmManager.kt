package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries

import com.portalgmpy.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class GetOinvPendientesExportarUseCaseAlarmManager @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    suspend  fun getPendientes(): DatosMovimientosOinv  {
        return OinvRepository.getTest()
    }

}