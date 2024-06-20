package com.portalgmpy.y_trackcomercial.repository

import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.OcrdOitmClient
import com.portalgmpy.y_trackcomercial.data.model.entities.OcrdOitmEntity
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OcrdOitmRepository @Inject constructor(
    private val ocrdoitmClient: OcrdOitmClient,
    private val sharedPreferences: SharedPreferences,
    private val ocrdOitmDao: com.portalgmpy.y_trackcomercial.data.model.dao.OcrdOitmDao
    ) {


   /* suspend fun fetchOcrdOitm(): Int {
        return withContext(Dispatchers.IO) {
            // Borra todos los datos de la tabla antes de insertar nuevos datos
            ocrdOitmDao.deleteAllOcrdOitm()
            val Oitm = ocrdoitmClient.getOcrditem(sharedPreferences.getToken().toString())
            ocrdOitmDao.insertAllOcrdOitm(Oitm)
            return@withContext getCountOcrdOitm()
        }
    }*/
   suspend fun fetchOcrdOitm(): Int {
       return withContext(Dispatchers.IO) {
           try {
               // Borra todos los datos de la tabla antes de insertar nuevos datos
               ocrdOitmDao.deleteAllOcrdOitm()
               val Oitm = ocrdoitmClient.getOcrditem(sharedPreferences.getToken().toString())
               ocrdOitmDao.insertAllOcrdOitm(Oitm)
               return@withContext getCountOcrdOitm()
           } catch (e: Exception) {
               // Manejar la excepción y devolver 1
               Log.e("Error", "Error al obtener OcrdOitm: ${e.message}")
               return@withContext -1
           }
       }
   }

    suspend fun insertAllOitmXocrd(list:List<OcrdOitmEntity>){
        ocrdOitmDao.deleteAllOcrdOitm()
        ocrdOitmDao.insertAllOcrdOitm(list)
    }

    fun getCountOcrdOitm(): Int = ocrdOitmDao.getOcrdOitmCount()

 }