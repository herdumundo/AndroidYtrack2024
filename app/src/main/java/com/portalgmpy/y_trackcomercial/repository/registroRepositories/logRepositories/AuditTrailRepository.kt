package com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories

import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.exportaciones.ExportacionAuditTrailApiClient
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgmpy.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.portalgmpy.y_trackcomercial.data.model.entities.logs.AuditTrailEntity
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import com.portalgmpy.y_trackcomercial.util.logUtils.LogUtils
import java.time.LocalDateTime
import javax.inject.Inject

class AuditTrailRepository @Inject constructor
    (private val auditTrailDao:  AuditTrailDao,
     private val exportacionAuditTrailApiClient: ExportacionAuditTrailApiClient, // Paso 1: Agregar el ApiClient al constructor
     private val sharedPreferences: SharedPreferences,
     private val logRepository: LogRepository   ) {
    suspend fun insertAuditTrailDao(auditTrailEntity: AuditTrailEntity): Long {
        return auditTrailDao.insertAuditTrailDao(auditTrailEntity)
    }

   suspend fun getAuditTrailCount():  Int  {
        return auditTrailDao.getCountAuditTrail()
    }
    suspend fun getUltimaHoraRegistro():  Long  {
        return auditTrailDao.getUltimoRegistroHoraLong()
    }

    suspend fun getAllLotesAuditTrail(): List<lotesDeAuditoriaTrail> {
        val lotesDeAuditoriaEntity = auditTrailDao.getAllTrailExportar()
        return lotesDeAuditoriaEntity.map { entity ->
            lotesDeAuditoriaTrail(
                id = entity.id,
                Fecha = entity.Fecha,
                fechaLong =  entity.fechaLong,
                idUsuario = entity.idUsuario,
                latitud = entity.latitud,
                longitud = entity.longitud,
                velocidad = entity.velocidad,
                nombreUsuario = entity.nombreUsuario,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                bateria = entity.bateria,
                idVisita = entity.idVisita,
                distanciaPV = entity.distanciaPV,
                tiempo = entity.tiempo,
                tipoRegistro = entity.tipoRegistro
            )
        }
    }

    suspend fun exportarAuditTrail(lotesAuditTrail: EnviarAuditoriaTrailRequest) {
        try {
            val apiResponse = exportacionAuditTrailApiClient.uploadAuditoriaTrailData(lotesAuditTrail,sharedPreferences.getToken().toString())
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
              // auditTrailDao.updateExportadoCerrado()
                val idsLoteActual = lotesAuditTrail.lotesDeAuditoriaTrail.map { it.id!! }
                 auditTrailDao.eliminarRegistrosPorLotes(idsLoteActual)
            }
            Log.i("ExportacionAuditTrail",apiResponse.msg)

        } catch (e: Exception) {

  /*          val idsLoteActual = lotesAuditTrail.lotesDeAuditoriaTrail.map { it.id!! }
            //  val idsString = idsLoteActual.joinToString(",")
            auditTrailDao.updateExportadoCerradoPorLotes(idsLoteActual)
*/
           // Log.i("ErrorAudit",e.toString())
            LogUtils.insertLog(logRepository,
                LocalDateTime.now().toString(),
                "EXPORTACION AUDITTRAIL",
                e.toString(),sharedPreferences.getUserId(),sharedPreferences.getUserName()!!,"EXPORTACIONES",0)
        }
    }
}
