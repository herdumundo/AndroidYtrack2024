package com.portalgmpy.y_trackcomercial.usecases.ocrd

import com.portalgmpy.y_trackcomercial.data.model.entities.OCRDEntity
import com.portalgmpy.y_trackcomercial.repository.CustomerRepository
import javax.inject.Inject


class GetDatosClienteUseCase @Inject constructor(
    private val ocrdRepository: CustomerRepository
)
{
    suspend fun getDatosCliente(): List<OCRDEntity> {
        return ocrdRepository.getDatosCliente()
    }
}