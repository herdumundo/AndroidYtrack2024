package com.portalgmpy.y_trackcomercial.usecases.ventas.inv1

 import com.portalgmpy.y_trackcomercial.data.model.models.ventas.Inv1Detalle
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.INV1_REPOSITORY
 import javax.inject.Inject

class InsertInv1UseCase @Inject constructor(
    private val repository: INV1_REPOSITORY
)
{
    suspend fun Insertar(lotesList: List<Inv1Detalle>) {
        return repository.insertInv1Bulk(lotesList)
    }
}