package com.portalgmpy.y_trackcomercial.usecases.alarmManager.log

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeActividades
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class GetLogPendienteUseCaseAlarmManager @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend  fun getAuditLogPendientes(): List<lotesDeActividades> {
        return logRepository.getAllLotesAuditLog()
    }
}