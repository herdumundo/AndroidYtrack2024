package com.portalgmpy.y_trackcomercial.usecases.visitas

import com.portalgmpy.y_trackcomercial.data.model.models.HorasTranscurridasPv
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetHorasTranscurridasVisitasUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getHorasTranscurridasVisitas(): List<HorasTranscurridasPv> {
        return visitasRepository.getHorasTranscurridasPunto()
    }
}