package com.portalgmpy.y_trackcomercial.data.api.exportaciones


import com.portalgmpy.y_trackcomercial.data.api.response.ApiResponse
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarVisitasRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionVisitasApiClient {
    @POST("/exportarVisitasversiondos")
    suspend fun uploadVisitasData(@Body lotesVisitas: EnviarVisitasRequest,@Header("Authorization") authorization: String): ApiResponse
}