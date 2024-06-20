package com.portalgmpy.y_trackcomercial.usecases.ubicacionesPv

import com.portalgmpy.y_trackcomercial.repository.UbicacionesPvRepository
import javax.inject.Inject

class ImportarUbicacionesPvUseCase @Inject constructor(
    private val ubicacionesPvRepository: UbicacionesPvRepository
) {
    suspend fun fetchUbicacionesPv(): Int {
        return ubicacionesPvRepository.fetchUbicacionesPv()
    }
}
