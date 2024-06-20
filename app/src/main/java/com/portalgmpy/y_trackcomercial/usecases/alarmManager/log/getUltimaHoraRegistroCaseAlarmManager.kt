package com.portalgmpy.y_trackcomercial.usecases.alarmManager.log

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetUltimaHoraRegistroUseCaseAlarmManager @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun GetUltimaHoraRegistroUseCase(): Long {
        return auditTrailRepository.getUltimaHoraRegistro()
    }
}