package com.portalgmpy.y_trackcomercial.usecases.nuevaUbicacion

import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeUbicacionesNuevas
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject


class GetNuevasUbicacionesPendientesUseCase  @Inject constructor(
    private val NuevaUbicacionRepository: NuevaUbicacionRepository
) {

    suspend  fun GetPendientes(): List<lotesDeUbicacionesNuevas> {
        return NuevaUbicacionRepository.getAllLotesNuevasUbicaciones()
    }
}