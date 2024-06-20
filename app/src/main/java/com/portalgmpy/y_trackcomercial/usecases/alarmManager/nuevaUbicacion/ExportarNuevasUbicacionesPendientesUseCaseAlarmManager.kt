package com.portalgmpy.y_trackcomercial.usecases.alarmManager.nuevaUbicacion

import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject


class ExportarNuevasUbicacionesPendientesUseCaseAlarmManager  @Inject constructor(
    private val nuevaUbicacionRepository: NuevaUbicacionRepository
) {
    suspend fun enviarPendientes(lotes: EnviarLotesDeUbicacionesNuevasRequest) {
        return nuevaUbicacionRepository.exportarMovimientos(lotes)
    }
}