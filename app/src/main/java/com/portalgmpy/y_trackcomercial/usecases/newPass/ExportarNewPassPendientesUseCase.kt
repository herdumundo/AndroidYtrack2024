package com.portalgmpy.y_trackcomercial.usecases.newPass

import com.portalgmpy.y_trackcomercial.data.api.request.NuevoPass
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NewPassRepository
import javax.inject.Inject


class ExportarNewPassPendientesUseCase  @Inject constructor(
    private val newPassRepository: NewPassRepository
) {
    suspend fun enviarPendientes(lotes: NuevoPass) {
        return newPassRepository.exportarDatos(lotes)
    }
}