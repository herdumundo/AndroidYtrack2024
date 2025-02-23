package com.portalgmpy.y_trackcomercial.usecases.parametros

import com.portalgmpy.y_trackcomercial.repository.ParametrosRepository
import javax.inject.Inject

class ImportarParametrosUseCase @Inject constructor(
    private val parametrosRepository: ParametrosRepository
) {
    suspend  fun importarParametros() {
        parametrosRepository.fetchParametros()
    }
}