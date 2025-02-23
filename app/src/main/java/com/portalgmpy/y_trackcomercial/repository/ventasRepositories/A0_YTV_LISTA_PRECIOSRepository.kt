package com.portalgmpy.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.A0_YTV_LISTA_PRECIOSClient
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_LISTA_PRECIOSDAO
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class A0_YTV_LISTA_PRECIOSRepository @Inject constructor(
    private val A0_YTV_LISTA_PRECIOSClient: A0_YTV_LISTA_PRECIOSClient,
    private val A0_YTV_LISTA_PRECIOSDao: A0_YTV_LISTA_PRECIOSDAO,
    private val sharedPreferences: SharedPreferences,
) {
   /* suspend fun sincronizarApi(): Int {
        return withContext(Dispatchers.IO) {
            val datos = A0_YTV_LISTA_PRECIOSClient.getDatos( sharedPreferences.getToken().toString())

            Log.i("MensajeYtrack",datos.toString())
            A0_YTV_LISTA_PRECIOSDao.eliminarTodos()
            A0_YTV_LISTA_PRECIOSDao.insertAll(datos)
            return@withContext getTotalCount()
        }
    }*/
   suspend fun sincronizarApi(): Int {
       return withContext(Dispatchers.IO) {
           try {
               val datos = A0_YTV_LISTA_PRECIOSClient.getDatos(sharedPreferences.getToken().toString())

               Log.i("MensajeYtrack", datos.toString())
               A0_YTV_LISTA_PRECIOSDao.eliminarTodos()
               A0_YTV_LISTA_PRECIOSDao.insertAll(datos)
               return@withContext getTotalCount()
           } catch (e: Exception) {
               // Manejar la excepción y devolver 1
               Log.e("Error", "Error al sincronizar API: ${e.message}")
               return@withContext -1
           }
       }
   }

    fun getTotalCount():  Int  {
        return A0_YTV_LISTA_PRECIOSDao.getCount()
    }

}