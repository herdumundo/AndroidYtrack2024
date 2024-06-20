package com.portalgmpy.y_trackcomercial.data.api

 import com.portalgmpy.y_trackcomercial.data.model.entities.LotesListasEntity
 import retrofit2.http.Header
 import retrofit2.http.POST
 import javax.inject.Singleton
@Singleton
interface LotesListasApiClient {
    @POST("/LotesListas") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getListasLotes(@Header("Authorization") authorization: String): List<LotesListasEntity>
}



