package com.portalgmpy.y_trackcomercial.repository

import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.OitmClient
import com.portalgmpy.y_trackcomercial.data.model.entities.OitmEntity
import com.portalgmpy.y_trackcomercial.data.model.models.OitmItem
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OitmRepository @Inject constructor(
    private val oitmClient: OitmClient,
    private val sharedPreferences: SharedPreferences,
    private val oitmDao: com.portalgmpy.y_trackcomercial.data.model.dao.OitmDao
    ) {



    /*suspend fun fetchOitm(): Int {
        return withContext(Dispatchers.IO) {
            val Oitm = oitmClient.getOITM(sharedPreferences.getToken().toString())
            oitmDao.DeleteAllOitm()
            oitmDao.insertAllOitm(Oitm)
            return@withContext getCountOitm()
        }
    }*/
    suspend fun fetchOitm(): Int {
        return withContext(Dispatchers.IO) {
            try {
                val Oitm = oitmClient.getOITM(sharedPreferences.getToken().toString())
                oitmDao.DeleteAllOitm()
                oitmDao.insertAllOitm(Oitm)
                return@withContext getCountOitm()
            } catch (e: Exception) {
                // Manejar la excepción y devolver 1
                Log.e("Error", "Error al obtener OITM: ${e.message}")
                return@withContext -1
            }
        }
    }


    suspend fun insertOitm(list:List<OitmEntity>){
        oitmDao.DeleteAllOitm()
        oitmDao.insertAllOitm(list)
    }

    fun getCountOitm(): Int = oitmDao.getOitmCount()

    fun getOitm(): List<OitmItem> = oitmDao.getOitm()


    fun getOitmByPuntoVentaOpen(): List<OitmItem> = oitmDao.getOitmByPuntoVentaAbierto()



}