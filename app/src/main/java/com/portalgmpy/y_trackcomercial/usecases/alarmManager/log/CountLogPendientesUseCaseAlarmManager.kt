package com.portalgmpy.y_trackcomercial.usecases.alarmManager.log

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class CountLogPendientesUseCaseAlarmManager  @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend  fun CountPendientes(): Int {
        return logRepository.getCountLog()
    }
}