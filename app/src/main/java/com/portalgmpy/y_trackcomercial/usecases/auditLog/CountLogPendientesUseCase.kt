package com.portalgmpy.y_trackcomercial.usecases.auditLog

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class CountLogPendientesUseCase  @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend  fun CountPendientes(): Int {
        return logRepository.getCountLog()
    }
}