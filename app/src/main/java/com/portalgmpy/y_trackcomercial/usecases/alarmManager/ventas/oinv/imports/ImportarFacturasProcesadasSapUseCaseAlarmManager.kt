package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.imports
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ImportarFacturasProcesadasSapUseCaseAlarmManager @Inject constructor(
    private val repository: OinvRepository
) {

    suspend  fun exportarDatos() {
        return repository.importarFacturasProcesadasDocentries()
    }
}