package com.portalgmpy.y_trackcomercial.usecases.ocrd

import com.portalgmpy.y_trackcomercial.repository.CustomerRepository
import javax.inject.Inject


class ImportarOcrdUseCase @Inject constructor(
    private val ocrdRepository: CustomerRepository
)
{
    suspend fun Importar(): Int {
        return ocrdRepository.fetchCustomers()
    }
}