package com.portalgmpy.y_trackcomercial.data.api

 import com.portalgmpy.y_trackcomercial.data.model.entities.OcrdUbicacionEntity
 import retrofit2.http.Header
 import retrofit2.http.POST
 import javax.inject.Singleton
@Singleton
interface OcrdUbicacionesApiClient {
    @POST("/OCRD_UBICACIONES")
    suspend fun getOcrdUbicaciones(@Header("Authorization") authorization: String): List<OcrdUbicacionEntity>
}



