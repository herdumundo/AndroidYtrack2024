package com.portalgmpy.y_trackcomercial.usecases.auditLog

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetUltimaHoraRegistroUseCase @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun GetUltimaHoraRegistroUseCase(): Long {
        return auditTrailRepository.getUltimaHoraRegistro()
    }
}