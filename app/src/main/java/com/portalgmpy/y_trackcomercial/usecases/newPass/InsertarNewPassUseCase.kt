package com.portalgmpy.y_trackcomercial.usecases.newPass

import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NewPassRepository
import javax.inject.Inject


class InsertarNewPassUseCase @Inject constructor(
    private val newPassRepository: NewPassRepository
)
{
    suspend fun Insertar(datos: NewPassEntity): Long {
        return newPassRepository.insertNewPass(datos)
    }
}

