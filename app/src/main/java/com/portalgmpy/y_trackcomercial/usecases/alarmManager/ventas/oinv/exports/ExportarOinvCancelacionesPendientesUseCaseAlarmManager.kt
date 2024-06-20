package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ExportarOinvCancelacionesPendientesUseCaseAlarmManager @Inject constructor(
    private val repository: OinvRepository
) {

    suspend  fun exportarDatos() {
        return repository.exportarDatosCancelacion()
    }
}