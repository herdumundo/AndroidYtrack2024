package com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.listaPrecios

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YTV_LISTA_PRECIOSRepository
import javax.inject.Inject

class ImportarListaPreciosUseCaseAlarmManager @Inject constructor(
    private val A0_YTV_LISTA_PRECIOSRepository: A0_YTV_LISTA_PRECIOSRepository
)
{
    suspend fun Importar(): Int {
        return A0_YTV_LISTA_PRECIOSRepository.sincronizarApi()
    }
}