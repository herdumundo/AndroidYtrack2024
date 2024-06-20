package com.portalgmpy.y_trackcomercial.usecases.alarmManager.log

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class EnviarLogPendientesUseCaseAlarmManager @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend fun enviarLogPendientes(lotes: EnviarLotesDeActividadesRequest) {
        return logRepository.exportarLog(lotes)
    }
}