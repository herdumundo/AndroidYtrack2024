package com.portalgmpy.y_trackcomercial.core.di


import com.portalgmpy.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YTV_LISTA_PRECIOSRepository
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YTV_ORDEN_VENTARepository
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import com.portalgmpy.y_trackcomercial.repository.ventasRepositories.OinvRepository
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail.CountAuditTrailUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail.EnviarAuditTrailPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail.GetAuditTrailPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.CountCantidadPendientesAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.EnviarVisitasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.GetVisitasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.log.CountLogPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.log.EnviarLogPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.log.GetLogPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.listaPrecios.ImportarListaPreciosUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.ordenVenta.ImportarOrdenVentaUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.stockAlmacen.ImportarStockAlmacenUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.CountNroFacturaPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.GetNroFacturaPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvCancelacionesPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.imports.ImportarFacturasProcesadasSapUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvNoProcesadasSapUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesCancelacionesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.GetOinvPendientesExportarUseCaseAlarmManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmManagerModule {

    @Provides
    @Singleton
    fun provideGetAuditTrailPendienteUseCaseAlarmManager(
        auditTrailRepository: AuditTrailRepository
    ): GetAuditTrailPendienteUseCaseAlarmManager {
        return GetAuditTrailPendienteUseCaseAlarmManager(auditTrailRepository)
    }



    @Provides
    @Singleton
    fun provideEnviarAuditTrailPendientesUseCaseAlarmManager(
        auditTrailRepository: AuditTrailRepository
    ): EnviarAuditTrailPendientesUseCaseAlarmManager {
        return EnviarAuditTrailPendientesUseCaseAlarmManager(auditTrailRepository)
    }


    @Provides
    @Singleton
    fun provideCountAuditTrailUseCaseAlarmManager(
        auditTrailRepository: AuditTrailRepository
    ): CountAuditTrailUseCaseAlarmManager {
        return CountAuditTrailUseCaseAlarmManager(auditTrailRepository)
    }



    @Provides
    @Singleton
    fun providegetgetVisitasPendientesUseCaseAlarmManager(
        visitasRepository: VisitasRepository
    ): GetVisitasPendientesUseCaseAlarmManager {
        return GetVisitasPendientesUseCaseAlarmManager(visitasRepository)
    }

    @Provides
    @Singleton
    fun providegetEnviarVisitasPendientesUseCaseAlarmManager(
        visitasRepository: VisitasRepository
    ): EnviarVisitasPendientesUseCaseAlarmManager {
        return EnviarVisitasPendientesUseCaseAlarmManager(visitasRepository)
    }


    @Provides
    @Singleton
    fun provideCountCantidadPendientesUseCaseAlarmManager(
        visitasRepository: VisitasRepository
    ): CountCantidadPendientesAlarmManager {
        return CountCantidadPendientesAlarmManager(visitasRepository)
    }



    @Provides
    @Singleton
    fun provideCountLogPendientesUseCaseAlarmManager(
        logRepository: LogRepository
    ): CountLogPendientesUseCaseAlarmManager {
        return CountLogPendientesUseCaseAlarmManager(logRepository)
    }

    @Provides
    @Singleton
    fun provideGetLogPendienteUseCaseAlarmManager(
        logRepository: LogRepository
    ): GetLogPendienteUseCaseAlarmManager {
        return GetLogPendienteUseCaseAlarmManager(logRepository)
    }

    @Provides
    @Singleton
    fun provideEnviarLogPendientesUseCaseAlarmManager(
        logRepository: LogRepository
    ): EnviarLogPendientesUseCaseAlarmManager {
        return EnviarLogPendientesUseCaseAlarmManager(logRepository)
    }


    @Provides
    @Singleton
    fun provideExportarOinvCancelacionesPendientesUseCaseAlarmManager(
        repository: OinvRepository
    ): ExportarOinvCancelacionesPendientesUseCaseAlarmManager {
        return ExportarOinvCancelacionesPendientesUseCaseAlarmManager(repository)
    }

    @Provides
    @Singleton
    fun provideExportarOinvPendientesUseCaseAlarmManager(
        repository: OinvRepository
    ): ExportarOinvPendientesUseCaseAlarmManager {
        return ExportarOinvPendientesUseCaseAlarmManager(repository)
    }

    @Provides
    @Singleton
    fun provideImportarFacturasProcesadasSapUseCaseAlarmManager(
        repository: OinvRepository
    ): ImportarFacturasProcesadasSapUseCaseAlarmManager {
        return ImportarFacturasProcesadasSapUseCaseAlarmManager(repository)
    }
    @Provides
    @Singleton
    fun provideCountOinvNoProcesadasSapUseCaseAlarmManager(
        repository: OinvRepository
    ): CountOinvNoProcesadasSapUseCaseAlarmManager {
        return CountOinvNoProcesadasSapUseCaseAlarmManager(repository)
    }
    @Provides
    @Singleton
    fun provideCountOinvPendientesCancelacionesUseCaseAlarmManager(
        repository: OinvRepository
    ): CountOinvPendientesCancelacionesUseCaseAlarmManager {
        return CountOinvPendientesCancelacionesUseCaseAlarmManager(repository)
    }

    @Provides
    @Singleton
    fun provideCountOinvPendientesUseCaseAlarmManager(
        repository: OinvRepository
    ): CountOinvPendientesUseCaseAlarmManager {
        return CountOinvPendientesUseCaseAlarmManager(repository)
    }

    @Provides
    @Singleton
    fun provideGetOinvPendientesExportarUseCaseAlarmManager(
        repository: OinvRepository
    ): GetOinvPendientesExportarUseCaseAlarmManager {
        return GetOinvPendientesExportarUseCaseAlarmManager(repository)
    }
    @Provides
    @Singleton
    fun provideCountNroFacturaPendienteUseCaseAlarmManager(
        repository: A0_YtvVentasRepository
    ): CountNroFacturaPendienteUseCaseAlarmManager {
        return CountNroFacturaPendienteUseCaseAlarmManager(repository)
    }
    @Provides
    @Singleton
    fun provideGetNroFacturaPendienteUseCaseAlarmManager(
        repository: A0_YtvVentasRepository
    ): GetNroFacturaPendienteUseCaseAlarmManager {
        return GetNroFacturaPendienteUseCaseAlarmManager(repository)
    }

    @Provides
    @Singleton
    fun provideImportarOrdenVentaUseCaseAlarmManager(
        repository: A0_YTV_ORDEN_VENTARepository
    ): ImportarOrdenVentaUseCaseAlarmManager {
        return ImportarOrdenVentaUseCaseAlarmManager(repository)
    }
    @Provides
    @Singleton
    fun provideImportarStockAlmacenUseCaseAlarmManager(
        repository: A0_YTV_STOCK_ALMACENRepository
    ): ImportarStockAlmacenUseCaseAlarmManager {
        return ImportarStockAlmacenUseCaseAlarmManager(repository)
    }

    @Provides
    @Singleton
    fun provideImportarListaPreciosUseCaseAlarmManager(
        repository: A0_YTV_LISTA_PRECIOSRepository
    ): ImportarListaPreciosUseCaseAlarmManager {
        return ImportarListaPreciosUseCaseAlarmManager(repository)
    }

}
