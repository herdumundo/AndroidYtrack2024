package com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class EnviarVisitasPendientesUseCaseAlarmManager @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun enviarVisitasPendientes(lotesVisitas: EnviarVisitasRequest) {
        return visitasRepository.exportarVisitas(lotesVisitas)
    }
}