package com.portalgmpy.y_trackcomercial.usecases.ventas.ordenVenta

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YTV_ORDEN_VENTARepository
import javax.inject.Inject

class ImportarOrdenVentaUseCase @Inject constructor(
    private val repository: A0_YTV_ORDEN_VENTARepository
)
{
    suspend fun Importar(): Int {
        return repository.sincronizarApi()
    }
}