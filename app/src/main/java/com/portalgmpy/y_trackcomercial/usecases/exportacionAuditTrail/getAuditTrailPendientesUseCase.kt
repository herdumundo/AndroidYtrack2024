package com.portalgmpy.y_trackcomercial.usecases.exportacionAuditTrail

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetAuditTrailPendienteUseCase @Inject constructor(
private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun getAuditTrailPendientes(): List<lotesDeAuditoriaTrail> {
        return auditTrailRepository.getAllLotesAuditTrail()
    }
}