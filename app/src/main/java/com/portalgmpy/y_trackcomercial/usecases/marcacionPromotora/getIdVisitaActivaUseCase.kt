package com.portalgmpy.y_trackcomercial.usecases.marcacionPromotora

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetIdVisitaActivaUseCase @Inject constructor(
    private val visitasRepository: VisitasRepository
) {
    // suspend
    suspend  fun getActiveVisitId(): Long {
        return visitasRepository.getIdVisitaActiva()
    }
}