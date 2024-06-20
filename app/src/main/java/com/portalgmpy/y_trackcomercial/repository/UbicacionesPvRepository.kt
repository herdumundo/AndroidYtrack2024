package com.portalgmpy.y_trackcomercial.repository

import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.UbicacionesPVClient
import com.portalgmpy.y_trackcomercial.data.model.dao.UbicacionesPvDao
import com.portalgmpy.y_trackcomercial.data.model.models.UbicacionPv
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UbicacionesPvRepository @Inject constructor(
    private val ubicacionesPVClient: UbicacionesPVClient,
    private val sharedPreferences: SharedPreferences,
    private val ubicacionesPvDao:  UbicacionesPvDao
    ) {
    /*suspend fun fetchUbicacionesPv(): Int {
        return withContext(Dispatchers.IO) {
            val ocrdUbicaciones =  ubicacionesPVClient.getUbicacionesPv(sharedPreferences.getToken().toString())
            ubicacionesPvDao.insertAllUbicacionesPv(ocrdUbicaciones)
            return@withContext getUbicacionesPvCount()
        }
    }*/
    suspend fun fetchUbicacionesPv(): Int {
        return withContext(Dispatchers.IO) {
            try {
                val ocrdUbicaciones = ubicacionesPVClient.getUbicacionesPv(sharedPreferences.getToken().toString())
                ubicacionesPvDao.insertAllUbicacionesPv(ocrdUbicaciones)
                return@withContext getUbicacionesPvCount()
            } catch (e: Exception) {
                // Manejar la excepción y devolver 1
                Log.e("Error", "Error al obtener ubicaciones Pv: ${e.message}")
                return@withContext -1
            }
        }
    }

    fun getUbicacionesPvCount():  Int  {
        return ubicacionesPvDao.getUbicacionesPvCount()
    }

    fun getUbicaciones(): List<UbicacionPv> {
        return ubicacionesPvDao.getUbicaciones()
    }
}