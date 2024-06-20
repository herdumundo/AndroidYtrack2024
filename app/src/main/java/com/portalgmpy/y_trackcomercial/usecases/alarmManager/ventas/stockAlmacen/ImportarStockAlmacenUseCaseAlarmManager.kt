package com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.stockAlmacen

import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject

class ImportarStockAlmacenUseCaseAlarmManager @Inject constructor(
    private val A0_YTV_STOCK_ALMACENRepository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun Importar(): Int {
        return A0_YTV_STOCK_ALMACENRepository.sincronizarApi()
    }
}