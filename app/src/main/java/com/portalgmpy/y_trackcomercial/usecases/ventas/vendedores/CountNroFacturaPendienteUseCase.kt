package com.portalgmpy.y_trackcomercial.usecases.ventas.vendedores

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject

class CountNroFacturaPendienteUseCase @Inject constructor(
    private val repository: A0_YtvVentasRepository
)
{
    suspend fun Obtener(): Int {
        return repository.getNroFacturaPendienteCount()
    }
}