package com.portalgmpy.y_trackcomercial.usecases.auditLog

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class EnviarLogPendientesUseCase @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend fun enviarLogPendientes(lotes: EnviarLotesDeActividadesRequest) {
        return logRepository.exportarLog(lotes)
    }
}