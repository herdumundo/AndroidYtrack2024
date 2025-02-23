package com.portalgmpy.y_trackcomercial.repository.registroRepositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.portalgmpy.y_trackcomercial.BuildConfig
import com.portalgmpy.y_trackcomercial.data.api.exportaciones.ExportacionVisitasApiClient
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeVisitas
import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity
import com.portalgmpy.y_trackcomercial.data.model.models.HorasTranscurridasPv
import com.portalgmpy.y_trackcomercial.data.model.models.LatitudLongitudPVIniciado
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class VisitasRepository @Inject constructor
    (
    private val visitasDao: com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.VisitasDao,
    private val sharedPreferences: SharedPreferences,
    private val exportacionVisitasApiService: ExportacionVisitasApiClient // Paso 1: Agregar el ApiClient al constructor

) {


    suspend fun insertVisita(visita: VisitasEntity): Long {
        return visitasDao.insertVisitas(visita)
    }


    suspend fun getVisitaByEstado(estado: String):  VisitasEntity? {
        return visitasDao.getVisitaByEstado(estado)
    }

    suspend fun getVisitaActiva(estado: String): VisitasEntity? {
        return visitasDao.getVisitaActiva(estado)
    }


    suspend fun getIdVisitaActiva(): Long {
        return visitasDao.getIdVisitaActiva()
    }

    suspend fun getDatosVisitaActiva(): List<LatitudLongitudPVIniciado> {
        return visitasDao.getDatosVisitaActiva()
    }

    suspend fun getSecuenciaVisita(): Int {
        return visitasDao.getSecuenciaVisita()
    }
    suspend fun updateVisita(visita:  VisitasEntity) {
        visitasDao.updateVisita(
            visita.id,
            visita.createdAt,
            visita.createdAtLong,
            visita.latitudUsuario,
            visita.longitudUsuario,
            visita.porcentajeBateria,
            visita.distanciaMetros,
            visita.pendienteSincro,
            visita.exportado,
            visita.tipoRegistro,
            visita.tipoCierre
        )
    }


    fun getCantidadRegistrosPendientes(): LiveData<Int> {
        return visitasDao.getCantidadRegistrosPendientes()
    }

    suspend fun getCantidadPendientesExportar(): Int {
        return visitasDao.getCantidadPendientesExportar()
    }

    fun getOcrdNameRepository(): LiveData<String> {
        return visitasDao.getOcrdPendiente()
    }

    suspend fun getAllLotesVisitas(): List<lotesDeVisitas> {
        val visitasEntities = visitasDao.getVisitasExportaciones()

        return visitasEntities.map { entity ->
            lotesDeVisitas(
                idUsuario = entity.idUsuario,
                idOcrd = entity.idOcrd,
                createdAt = entity.createdAt,
                createdAtLong = entity.createdAtLong,
                latitudUsuario = entity.latitudUsuario.toString(),
                longitudUsuario = entity.longitudUsuario.toString(),
                latitudPV = entity.latitudPV.toString(),
                longitudPV = entity.longitudPV.toString(),
                porcentajeBateria = entity.porcentajeBateria,
                idA = entity.idA,
                idRol = entity.idRol,
                tipoRegistro = entity.tipoRegistro,
                distanciaMetros = entity.distanciaMetros,
                estadoVisita = entity.estadoVisita ?: "",
                llegadaTardia = entity.tarde,
                idTurno = entity.idTurno,
                ocrdName = entity.ocrdName ?: "",
                tipoCierre = entity.tipoCierre,
                rol = entity.rol,
                secuencia = entity.secuencia,
                id=entity.id.toString(),
                versionApp = BuildConfig.VERSION_NAME
            )
        }
    }
    suspend fun exportarVisitas(lotesVisitas: EnviarVisitasRequest) {
        try {
            val apiResponse = exportacionVisitasApiService.uploadVisitasData(lotesVisitas,sharedPreferences.toString())
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                visitasDao.updateExportadoCerrado()
            } else {
                // Manejar otros casos según el valor de "tipo"
            }
            Log.i("MensajeTest",apiResponse.msg)
        } catch (e: Exception) {
           Log.i("Mensaje",e.toString())
        }
    }

    suspend fun getHorasTranscurridasPunto(): List<HorasTranscurridasPv> {
        return visitasDao.getHorasTranscurridasPunto()
    }

    }