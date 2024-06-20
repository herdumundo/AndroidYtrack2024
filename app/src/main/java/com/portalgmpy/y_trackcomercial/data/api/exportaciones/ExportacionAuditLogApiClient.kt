package com.portalgmpy.y_trackcomercial.data.api.exportaciones

import com.portalgmpy.y_trackcomercial.data.api.response.ApiResponse
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditLogApiClient {
    @POST("/insertarAllActivityLog")
    suspend fun uploadAuditLoglData(@Body lotesDeActividades: EnviarLotesDeActividadesRequest,@Header("Authorization") authorization: String): ApiResponse
}