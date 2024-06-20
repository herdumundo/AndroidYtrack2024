package com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas

 import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class CountCantidadPendientesAlarmManager   @Inject constructor(
    private val visitasRepository: VisitasRepository
) {
    suspend  fun ContarCantidadPendientes(): Int {
        return visitasRepository.getCantidadPendientesExportar()
    }
}