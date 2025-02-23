package com.portalgmpy.y_trackcomercial.data.api.apiResponse

import com.portalgmpy.y_trackcomercial.data.api.UsuarioResponse
 import retrofit2.http.GET

interface UsuariosUbicacionesApi {
    @GET("ImportUsuarios")
    suspend fun getUsuarios(): List<UsuarioResponse>
}