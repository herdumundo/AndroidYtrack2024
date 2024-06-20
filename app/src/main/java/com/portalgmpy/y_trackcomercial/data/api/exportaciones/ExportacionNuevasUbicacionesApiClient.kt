package com.portalgmpy.y_trackcomercial.data.api.exportaciones

import com.portalgmpy.y_trackcomercial.data.api.response.ApiResponse
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionNuevasUbicacionesApiClient {
    @POST("/insertarAllNuevasUbicaciones")
    suspend fun uploadNuevasUbicaciones(@Body lotesDeUbicacionesNuevas: EnviarLotesDeUbicacionesNuevasRequest
                                      ,@Header("Authorization") authorization: String): ApiResponse
}