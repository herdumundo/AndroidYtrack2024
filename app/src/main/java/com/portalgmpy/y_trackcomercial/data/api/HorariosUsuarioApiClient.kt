package com.portalgmpy.y_trackcomercial.data.api

import com.portalgmpy.y_trackcomercial.data.model.models.HorariosUsuarioResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton


@Singleton
interface HorariosUsuarioApiClient {
    @POST("/horarioUsuario")
    @FormUrlEncoded
    suspend fun getHorarioUsuario(
        @Field("idUsuario") idUsuario: Int,
        @Header("Authorization") authorization: String
    )   : List<HorariosUsuarioResponse>
}