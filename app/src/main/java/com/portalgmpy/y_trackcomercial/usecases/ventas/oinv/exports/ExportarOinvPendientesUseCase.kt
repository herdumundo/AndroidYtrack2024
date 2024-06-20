package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports
import com.portalgmpy.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ExportarOinvPendientesUseCase @Inject constructor(
    private val repository: OinvRepository
) {
  /*  suspend  fun exportarDatos(lotes:  LotesMovimientosOinv) {
        return repository.exportarDatos(lotes)
    }
*/
    suspend  fun exportarDatos(lotes:   DatosMovimientosOinv ) {
        return repository.exportarDatos(lotes)
    }
}