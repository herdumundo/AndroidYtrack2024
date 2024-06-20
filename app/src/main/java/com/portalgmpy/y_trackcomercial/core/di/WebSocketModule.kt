package com.portalgmpy.y_trackcomercial.core.di

import com.portalgmpy.y_trackcomercial.repository.CustomerRepository
import com.portalgmpy.y_trackcomercial.repository.LotesListasRepository
import com.portalgmpy.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgmpy.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgmpy.y_trackcomercial.repository.OitmRepository
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgmpy.y_trackcomercial.services.websocket.PieSocketListener
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.CountCantidadPendientesAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.EnviarVisitasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.GetVisitasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.CountNroFacturaPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.ExportarNroFacturaPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.GetNroFacturaPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.imports.ImportarFacturasProcesadasSapUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvNoProcesadasSapUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.GetOinvPendientesExportarUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Ajusta el alcance según tus necesidades
object WebSocketModule {
    @Provides
    @Singleton
    fun provideWebSocketListener(
        customerRepository: CustomerRepository,
        lotesListasRepository: LotesListasRepository,
        ocrdUbicacionesRepository: OcrdUbicacionesRepository,
        ocrdOitmRepository: OcrdOitmRepository,
        oitmRepository: OitmRepository,
        auditTrailRepository: AuditTrailRepository,
        countOinvPendientesUseCaseAlarmManager: CountOinvPendientesUseCaseAlarmManager,
        getOinvPendientesExportarUseCaseAlarmManager: GetOinvPendientesExportarUseCaseAlarmManager,
        exportarOinvPendientesUseCaseAlarmManager: ExportarOinvPendientesUseCaseAlarmManager,
        countNroFacturaPendienteUseCaseAlarmManager: CountNroFacturaPendienteUseCaseAlarmManager,
        getNroFacturaPendienteUseCaseAlarmManager: GetNroFacturaPendienteUseCaseAlarmManager,
        exportarNroFacturaPendientesUseCaseAlarmManager: ExportarNroFacturaPendientesUseCaseAlarmManager,

        getVisitasPendientesUseCase: GetVisitasPendientesUseCaseAlarmManager,
        enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCaseAlarmManager,
        countCantidadPendientes: CountCantidadPendientesAlarmManager,

        countOinvNoProcesadasSapUseCaseAlarmManager: CountOinvNoProcesadasSapUseCaseAlarmManager,
        importarFacturasProcesadasSapUseCaseAlarmManager: ImportarFacturasProcesadasSapUseCaseAlarmManager,

        sharedPreferences: SharedPreferences
    ): PieSocketListener {
        return PieSocketListener(
            customerRepository,
            lotesListasRepository,
            ocrdUbicacionesRepository,
            ocrdOitmRepository,
            oitmRepository,
            auditTrailRepository,
            countOinvPendientesUseCaseAlarmManager,
            getOinvPendientesExportarUseCaseAlarmManager,
            exportarOinvPendientesUseCaseAlarmManager,
            countNroFacturaPendienteUseCaseAlarmManager,
            getNroFacturaPendienteUseCaseAlarmManager,
            exportarNroFacturaPendientesUseCaseAlarmManager,


            countOinvNoProcesadasSapUseCaseAlarmManager,
            importarFacturasProcesadasSapUseCaseAlarmManager,
            getVisitasPendientesUseCase ,
            enviarVisitasPendientesUseCase ,
            countCantidadPendientes ,
            sharedPreferences

        )
    }
}
