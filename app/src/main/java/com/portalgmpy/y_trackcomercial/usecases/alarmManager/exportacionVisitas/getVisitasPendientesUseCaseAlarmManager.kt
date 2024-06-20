package com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeVisitas
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetVisitasPendientesUseCaseAlarmManager @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getVisitasPendientes(): List<lotesDeVisitas> {
        return visitasRepository.getAllLotesVisitas()
    }
}