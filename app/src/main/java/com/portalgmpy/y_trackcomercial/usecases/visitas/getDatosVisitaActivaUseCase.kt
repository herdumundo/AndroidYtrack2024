package com.portalgmpy.y_trackcomercial.usecases.visitas

import com.portalgmpy.y_trackcomercial.data.model.models.LatitudLongitudPVIniciado
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetDatosVisitaActivaUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getDatosVisitaActivaUseCase(): List<LatitudLongitudPVIniciado> {
        return visitasRepository.getDatosVisitaActiva()
    }
}