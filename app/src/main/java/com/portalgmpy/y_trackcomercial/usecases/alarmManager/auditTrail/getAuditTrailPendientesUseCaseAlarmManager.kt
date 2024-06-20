package com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetAuditTrailPendienteUseCaseAlarmManager @Inject constructor(
private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun getAuditTrailPendientes(): List<lotesDeAuditoriaTrail> {
        return auditTrailRepository.getAllLotesAuditTrail()
    }
}