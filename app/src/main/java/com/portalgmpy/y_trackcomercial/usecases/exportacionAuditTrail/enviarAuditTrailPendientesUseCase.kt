package com.portalgmpy.y_trackcomercial.usecases.exportacionAuditTrail

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class EnviarAuditTrailPendientesUseCase @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun enviarAuditTrailPendientes(lotes: EnviarAuditoriaTrailRequest) {
        return auditTrailRepository.exportarAuditTrail(lotes)
    }
}