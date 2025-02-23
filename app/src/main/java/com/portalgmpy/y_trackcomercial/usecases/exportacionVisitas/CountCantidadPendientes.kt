package com.portalgmpy.y_trackcomercial.usecases.exportacionVisitas

 import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class CountCantidadPendientes  @Inject constructor(
    private val visitasRepository: VisitasRepository
) {
    suspend  fun ContarCantidadPendientes(): Int {
        return visitasRepository.getCantidadPendientesExportar()
    }
}