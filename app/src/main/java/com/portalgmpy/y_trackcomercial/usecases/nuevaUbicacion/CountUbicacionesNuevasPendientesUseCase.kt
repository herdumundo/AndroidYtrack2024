package com.portalgmpy.y_trackcomercial.usecases.nuevaUbicacion

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject


class CountUbicacionesNuevasPendientesUseCase  @Inject constructor(
    private val NuevaUbicacionRepository: NuevaUbicacionRepository
) {
    suspend  fun CountPendientes(): Int {
        return NuevaUbicacionRepository.getCount()
    }
}