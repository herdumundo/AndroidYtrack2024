package com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail

 import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
 import javax.inject.Inject

class CountAuditTrailUseCaseAlarmManager  @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
       suspend  fun CountPendientesExportacion(): Int {
        return auditTrailRepository.getAuditTrailCount()
    }
}