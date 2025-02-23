package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.actions

import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class InsertOinvUseCase @Inject constructor(
    private val repository: OinvRepository
)
{
    suspend fun Insertar(list: OINV_POS): Long {
        return repository.insertOinvPos(list)
    }
}