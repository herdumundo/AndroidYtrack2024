package com.portalgmpy.y_trackcomercial.usecases.ventas.inv1Lotes

import com.portalgmpy.y_trackcomercial.data.model.models.ventas.Inv1LoteDetalle
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.INV1_LOTES_REPOSITORY
import javax.inject.Inject
class InsertInv1LotesUseCase @Inject constructor(
    private val repository: INV1_LOTES_REPOSITORY
)
{
    suspend fun Insertar(lotesList: List<Inv1LoteDetalle>) {
        return repository.insertInv1LoteBulk(lotesList)
    }
}