package com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores

import com.portalgmpy.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject

class ExportarNroFacturaPendientesUseCaseAlarmManager @Inject constructor(
    private val repository: A0_YtvVentasRepository
) {

    suspend  fun exportarDatos(lotes: nroFacturaPendiente) {
        return repository.exportarDatos(lotes)
    }
}