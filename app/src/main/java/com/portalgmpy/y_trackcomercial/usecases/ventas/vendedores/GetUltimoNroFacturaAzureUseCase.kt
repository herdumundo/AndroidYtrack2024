package com.portalgmpy.y_trackcomercial.usecases.ventas.vendedores

import com.portalgmpy.y_trackcomercial.data.api.response.ApiResponseLimiteCreditoNroFactura
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject


class GetUltimoNroFacturaAzureUseCase @Inject constructor(
    private val repository: A0_YtvVentasRepository
)
{
    suspend fun Obtener(cardCode:String): ApiResponseLimiteCreditoNroFactura {
        return repository.consultarTransaccionFacturacion(cardCode )
    }
}