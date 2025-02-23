package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.actions

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class UpdateAnularFacturaOinvUseCase @Inject constructor(
    private val repository: OinvRepository
)
{
    suspend fun Update(docEntry: Long) {
        return repository.updateAnularFactura(docEntry)
    }
}