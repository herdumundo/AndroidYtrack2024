package com.portalgmpy.y_trackcomercial.usecases.newPass

import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NewPassRepository
import javax.inject.Inject


class CountNewPassPendienteUseCase  @Inject constructor(
    private val newPassRepository: NewPassRepository
) {
    suspend  fun CountPendientes(): Int {
        return newPassRepository.getCount()
    }
}