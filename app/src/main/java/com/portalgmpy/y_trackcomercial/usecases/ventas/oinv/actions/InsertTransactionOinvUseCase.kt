package com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.actions

import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class InsertTransactionOinvUseCase @Inject constructor(
    private val repository: OinvRepository
){
    suspend fun Insertar(OINV_POS: OINV_POS,INV1_POS: List<INV1_POS>, INV1_LOTES_POS: List<INV1_LOTES_POS>,idOrdenVenta:String,ultimoNroFactura:String):Long  {
        return repository.insertarVentaCompleta(OINV_POS,INV1_POS,INV1_LOTES_POS,idOrdenVenta,ultimoNroFactura)
    }
}