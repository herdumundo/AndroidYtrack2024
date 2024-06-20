package com.portalgmpy.y_trackcomercial.usecases.ventas.vendedores

import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject


class GetDatosFacturaUseCase @Inject constructor(
    private val repository: A0_YtvVentasRepository
)
{
    suspend fun Obtener(): List<A0_YTV_VENDEDOR_Entity> {
        return repository.getDatosFactura()
    }
}