package com.portalgmpy.y_trackcomercial.services.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgmpy.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgmpy.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
 import com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail.CountAuditTrailUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail.EnviarAuditTrailPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.auditTrail.GetAuditTrailPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.CountCantidadPendientesAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.EnviarVisitasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.exportacionVisitas.GetVisitasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.log.CountLogPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.log.EnviarLogPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.log.GetLogPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.movimientoInventario.CountMovimientoUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.movimientoInventario.EnviarMovimientoPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.movimientoInventario.GetMovimientoPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.listaPrecios.ImportarListaPreciosUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.ordenVenta.ImportarOrdenVentaUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.stockAlmacen.ImportarStockAlmacenUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.CountNroFacturaPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.ExportarNroFacturaPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.alarmManager.ventas.vendedores.GetNroFacturaPendienteUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvCancelacionesPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.imports.ImportarFacturasProcesadasSapUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvNoProcesadasSapUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesCancelacionesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.GetOinvPendientesExportarUseCaseAlarmManager
import com.portalgmpy.y_trackcomercial.util.SharedData
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import com.portalgmpy.y_trackcomercial.util.logUtils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
val batchSizes = 5
private const val MIN_TIME_INTERVAL: Long =  1000 // Intervalo de tiempo mínimo en milisegundos (ejemplo: 1000ms = 1 segundo)
private const val MIN_DISTANCE: Float = 0f // Distancia mínima en metros
private var gpsDelay = true
var tiempo = 0
var ejecutarBloque2 = false
@AndroidEntryPoint
class MyAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var countLogPendientesUseCase : CountLogPendientesUseCaseAlarmManager
    @Inject
    lateinit var countAuditTrailUseCase: CountAuditTrailUseCaseAlarmManager
    @Inject
    lateinit var countCantidadPendientes: CountCantidadPendientesAlarmManager
    @Inject
    lateinit var countMovimientoUseCase: CountMovimientoUseCaseAlarmManager
    @Inject
    lateinit var getVisitasPendientesUseCase: GetVisitasPendientesUseCaseAlarmManager
    @Inject
    lateinit var getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCaseAlarmManager
    @Inject
    lateinit var getLogPendienteUseCase: GetLogPendienteUseCaseAlarmManager
    @Inject
    lateinit var getMovimientoPendientesUseCase: GetMovimientoPendientesUseCaseAlarmManager
    @Inject
    lateinit var enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCaseAlarmManager
    @Inject
    lateinit var enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCaseAlarmManager
    @Inject
    lateinit var enviarLogPendientesUseCase: EnviarLogPendientesUseCaseAlarmManager
    @Inject
    lateinit var enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCaseAlarmManager
    @Inject
    lateinit var enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCaseAlarmManager
    @Inject
    lateinit var getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCaseAlarmManager
    @Inject
    lateinit var countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCaseAlarmManager
    @Inject
    lateinit var exportarOinvCancelacionesPendientesUseCaseAlarmManager: ExportarOinvCancelacionesPendientesUseCaseAlarmManager
    @Inject
    lateinit var exportarOinvPendientesUseCaseAlarmManager: ExportarOinvPendientesUseCaseAlarmManager
    @Inject
    lateinit var importarFacturasProcesadasSapUseCaseAlarmManager: ImportarFacturasProcesadasSapUseCaseAlarmManager
    @Inject
    lateinit var countOinvNoProcesadasSapUseCaseAlarmManager: CountOinvNoProcesadasSapUseCaseAlarmManager
    @Inject
    lateinit var countOinvPendientesCancelacionesUseCaseAlarmManager: CountOinvPendientesCancelacionesUseCaseAlarmManager
    @Inject
    lateinit var countOinvPendientesUseCaseAlarmManager: CountOinvPendientesUseCaseAlarmManager
    @Inject
    lateinit var getOinvPendientesExportarUseCaseAlarmManager: GetOinvPendientesExportarUseCaseAlarmManager
    @Inject
    lateinit var countNroFacturaPendienteUseCaseAlarmManager: CountNroFacturaPendienteUseCaseAlarmManager
    @Inject
    lateinit var getNroFacturaPendienteUseCaseAlarmManager: GetNroFacturaPendienteUseCaseAlarmManager
    @Inject
    lateinit var exportarNroFacturaPendientesUseCaseAlarmManager: ExportarNroFacturaPendientesUseCaseAlarmManager
    @Inject
    lateinit var importarListaPreciosUseCaseAlarmManager: ImportarListaPreciosUseCaseAlarmManager
    @Inject
    lateinit var importarStockAlmacenUseCaseAlarmManager: ImportarStockAlmacenUseCaseAlarmManager
    @Inject
    lateinit var importarOrdenVentaUseCaseAlarmManager: ImportarOrdenVentaUseCaseAlarmManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var auditTrailRepository: AuditTrailRepository
    private var locationUpdateManager: LocationUpdateManager? = null

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MyAlarmReceiver", "Alarm triggered")
         // Utiliza CoroutineScope para ejecutar la tarea suspendida
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            // Manejar la ubicación recibida
        }


        // Detener el LocationUpdateManager si ya está corriendo
        locationUpdateManager?.stopLocationUpdates()

        // Inicializar y comenzar las actualizaciones de ubicación
        locationUpdateManager = LocationUpdateManager(
            locationManager,
            locationListener,
        )
        locationUpdateManager?.startLocationUpdates()

        //ESTA PARTE ES PARA OBTENER LA UBICACION CADA 15 MINUTOS, E INSERTARLO EN LA BASE DE DATOS
        CoroutineScope(Dispatchers.Main).launch {
            // Lógica para obtener ubicación
            obtenerUbicacionGPS(
                context,
                LocationLocalViewModel(), // Obtén o injerta una instancia real de tu ViewModel
                locationListener,
                locationManager,
                sharedPreferences,
                auditTrailRepository
            )
            delay(15000) // 15 segundos
            locationManager.removeUpdates(locationListener)
            delay(2000) // 2 segundos en milisegundos
        }
        CoroutineScope(Dispatchers.IO).launch {
            val currentTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val currentTimeString = dateFormat.format(currentTime)
            // Verificar si la hora actual está entre las 07:00 y las 18:00
            if (currentTimeString >= "07:00" && currentTimeString <= "19:00") {
                Log.d("AlarmManagerCasoUso", "INTENTANDO EXPORTAR DATOS")
                try {
                exportarDatosAlarmManager(
                    countAuditTrailUseCase,
                    countCantidadPendientes,
                    countLogPendientesUseCase,
                    countMovimientoUseCase,
                    getVisitasPendientesUseCase,
                    getAuditTrailPendienteUseCase,
                    getLogPendienteUseCase,
                    getMovimientoPendientesUseCase,
                    enviarVisitasPendientesUseCase,
                    enviarAuditTrailPendientesUseCase,
                    enviarLogPendientesUseCase,
                    enviarMovimientoPendientesUseCase,
                    enviarNuevasUbicacionesPendientesUseCase,
                    getNuevasUbicacionesPendientesUseCase,
                    countUbicacionesNuevasPendientesUseCase,
                    exportarOinvCancelacionesPendientesUseCaseAlarmManager,
                    exportarOinvPendientesUseCaseAlarmManager,
                    importarFacturasProcesadasSapUseCaseAlarmManager,
                    countOinvNoProcesadasSapUseCaseAlarmManager,
                    countOinvPendientesCancelacionesUseCaseAlarmManager,
                    countOinvPendientesUseCaseAlarmManager,
                    getOinvPendientesExportarUseCaseAlarmManager,
                    countNroFacturaPendienteUseCaseAlarmManager,
                    getNroFacturaPendienteUseCaseAlarmManager,
                    exportarNroFacturaPendientesUseCaseAlarmManager,
                    importarListaPreciosUseCaseAlarmManager,
                    importarStockAlmacenUseCaseAlarmManager,
                    importarOrdenVentaUseCaseAlarmManager
                )
                } catch (e: Exception) {
                Log.e("AlarmManagerCasoUso", "Error executing task", e)
                }
            }
        }

        // Reprogramar la alarma
        scheduleExactAlarm(context)
    }
}

suspend fun exportarDatosAlarmManager(
    countAuditTrailUseCase: CountAuditTrailUseCaseAlarmManager,
    countCantidadPendientes: CountCantidadPendientesAlarmManager,
    countLogPendientesUseCase: CountLogPendientesUseCaseAlarmManager,
    countMovimientoUseCase: CountMovimientoUseCaseAlarmManager,
    getVisitasPendientesUseCase: GetVisitasPendientesUseCaseAlarmManager,
    getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCaseAlarmManager,
    getLogPendienteUseCase: GetLogPendienteUseCaseAlarmManager,
    getMovimientoPendientesUseCase: GetMovimientoPendientesUseCaseAlarmManager,
    enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCaseAlarmManager,
    enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCaseAlarmManager,
    enviarLogPendientesUseCase: EnviarLogPendientesUseCaseAlarmManager,
    enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCaseAlarmManager,
    enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCaseAlarmManager,
    getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCaseAlarmManager,
    countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCaseAlarmManager,
    exportarOinvCancelacionesPendientesUseCaseAlarmManager: ExportarOinvCancelacionesPendientesUseCaseAlarmManager,
    exportarOinvPendientesUseCaseAlarmManager: ExportarOinvPendientesUseCaseAlarmManager,
    importarFacturasProcesadasSapUseCaseAlarmManager: ImportarFacturasProcesadasSapUseCaseAlarmManager,
    countOinvNoProcesadasSapUseCaseAlarmManager: CountOinvNoProcesadasSapUseCaseAlarmManager,
    countOinvPendientesCancelacionesUseCaseAlarmManager: CountOinvPendientesCancelacionesUseCaseAlarmManager,
    countOinvPendientesUseCaseAlarmManager: CountOinvPendientesUseCaseAlarmManager,
    getOinvPendientesExportarUseCaseAlarmManager: GetOinvPendientesExportarUseCaseAlarmManager,
    countNroFacturaPendienteUseCaseAlarmManager: CountNroFacturaPendienteUseCaseAlarmManager,
    getNroFacturaPendienteUseCaseAlarmManager: GetNroFacturaPendienteUseCaseAlarmManager,
    exportarNroFacturaPendientesUseCaseAlarmManager: ExportarNroFacturaPendientesUseCaseAlarmManager,
    importarListaPreciosUseCaseAlarmManager: ImportarListaPreciosUseCaseAlarmManager,
    importarStockAlmacenUseCaseAlarmManager: ImportarStockAlmacenUseCaseAlarmManager,
    importarOrdenVentaUseCaseAlarmManager: ImportarOrdenVentaUseCaseAlarmManager
) {
    /**Proceso de importaciones de datos */
    importarListaPreciosUseCaseAlarmManager.Importar()
    importarStockAlmacenUseCaseAlarmManager.Importar()
    importarOrdenVentaUseCaseAlarmManager.Importar()
    /**Proceso de exportacion de datos */
     if (countNroFacturaPendienteUseCaseAlarmManager.Obtener() > 0) {
        val lotesPendientes =  getNroFacturaPendienteUseCaseAlarmManager.Obtener()
        exportarNroFacturaPendientesUseCaseAlarmManager.exportarDatos(lotesPendientes)
    }
    if (countOinvNoProcesadasSapUseCaseAlarmManager.CountPendientes() > 0) {
        importarFacturasProcesadasSapUseCaseAlarmManager.exportarDatos()
    }
    if (countOinvPendientesCancelacionesUseCaseAlarmManager.CountPendientes() > 0) {
        exportarOinvCancelacionesPendientesUseCaseAlarmManager.exportarDatos()
    }

    if (countOinvPendientesUseCaseAlarmManager.CountPendientes() > 0) {
        val lotesPendientes =  getOinvPendientesExportarUseCaseAlarmManager.getPendientes()
        exportarOinvPendientesUseCaseAlarmManager.exportarDatos(lotesPendientes)
    }
    if (countUbicacionesNuevasPendientesUseCase.CountPendientes() > 0) {
        val nuevasUbicacionesPendientes =
            getNuevasUbicacionesPendientesUseCase.GetPendientes()
        val enviarNuevaUbicacionRequest =
            EnviarLotesDeUbicacionesNuevasRequest(nuevasUbicacionesPendientes)
        enviarNuevasUbicacionesPendientesUseCase.enviarPendientes(
            enviarNuevaUbicacionRequest
        )
    }
    if (countCantidadPendientes.ContarCantidadPendientes() > 0) {
        val visitasPendientes =
            getVisitasPendientesUseCase.getVisitasPendientes()
        val enviarVisitasRequest = EnviarVisitasRequest(visitasPendientes)
        enviarVisitasPendientesUseCase.enviarVisitasPendientes(
            enviarVisitasRequest
        )
    }
    if (countAuditTrailUseCase.CountPendientesExportacion() > 0) {
        val auditTrailPendientes = getAuditTrailPendienteUseCase.getAuditTrailPendientes()
        val registrosPorBatches = auditTrailPendientes.chunked(batchSizes)
        registrosPorBatches.forEach { batch ->
            // Lógica para enviar el batch al servidor
            val enviarAuditTrailRequest = EnviarAuditoriaTrailRequest(batch)
            enviarAuditTrailPendientesUseCase.enviarAuditTrailPendientes(enviarAuditTrailRequest)
        }
    }
    if (countLogPendientesUseCase.CountPendientes() > 0) {
        val auditLogPendientes = getLogPendienteUseCase.getAuditLogPendientes()
        val registrosPorBatches = auditLogPendientes.chunked(batchSizes)
        registrosPorBatches.forEach { batch ->
            // Lógica para enviar el batch al servidor
            val enviarAuditLogRequest = EnviarLotesDeActividadesRequest(batch)
            enviarLogPendientesUseCase.enviarLogPendientes(enviarAuditLogRequest)
        }
    }
    if (countMovimientoUseCase.CountPendientes() > 0) {
        val movimientosPendientes =
            getMovimientoPendientesUseCase.GetPendientes()
        val enviarmovimientosRequest =
            EnviarLotesDeMovimientosRequest(movimientosPendientes)
        enviarMovimientoPendientesUseCase.enviarPendientes(
            enviarmovimientosRequest
        )
    }
    Log.d("AlarmManagerCasoUso", "FINALIZANDO EXPORTAR DATOS")

}


suspend fun obtenerUbicacionGPS(
    context: Context,
    locationViewModel: LocationLocalViewModel,
    locationListener: LocationListener,
    locationManager: LocationManager,
    sharedPreferences: SharedPreferences,
    auditTrailRepository: AuditTrailRepository
) {
    val sharedData = SharedData.getInstance()

    // Verificar si el proveedor de GPS está habilitado
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    locationViewModel.setGpsEnabled(isGpsEnabled)
    var c = 0;
    var a = 0;
    tiempo += 1
    if (isGpsEnabled && gpsDelay) {
        try {
            // Solicitar actualizaciones de ubicación
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_INTERVAL,
                MIN_DISTANCE,
                locationListener,

                )
            // Obtener la última ubicación conocida del proveedor de GPS
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Actualizar el ViewModel con la última ubicación conocida
            lastKnownLocation?.let {
                //lastKnownLocation se ejecuta en una sola llamada varias veces, entonce solo quiero que haga insert una vez, por eso el c++
                c++
              if (c == 1) {
                    sharedData.latitudUsuarioActual.value=it.latitude
                    sharedData.longitudUsuarioActual.value=it.longitude
                    val fechaLong = sharedData.fechaLongGlobal.value ?: System.currentTimeMillis()
                    sharedData.tiempo.value =  ((System.currentTimeMillis() - fechaLong) / 1000).toInt()

                    sharedData.distanciaPV.value = calcularDistancia(
                        it.latitude,
                        it.longitude,
                        sharedData.latitudPV.value ?: 0.0,
                        sharedData.longitudPV.value ?: 0.0).toInt()
                insertRoomLocation(
                    it.latitude, it.longitude,
                    context, sharedPreferences, auditTrailRepository,"EJECUCION POR MINUTOS")
                sharedData.fechaLongGlobal.value = System.currentTimeMillis()//SE COLOCA EL TIEMPO ACTUAL, CADA 3 PASOS SE VUELVE A ACTUALIZAR

              }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    } else {
        if (tiempo >= 20) {
            insertRoomLocation(
                1.0, 1.0,
                context, sharedPreferences, auditTrailRepository,"GPS INACTIVO"
            )
            tiempo = 0
        }
    }
}
suspend fun insertRoomLocation(
    latitud: Double,
    longitud: Double,
    context: Context,
    sharedPreferences: SharedPreferences,
    auditTrailRepository: AuditTrailRepository,
    tipoRegistro:String
) {

    val porceBateria = getBatteryPercentage(context)
    // Aquí puedes usar el auditTrailRepository para guardar la ubicación en Room
    if (sharedPreferences.getUserId().toString() == "0" || sharedPreferences.getUserId().toString()
            .isNullOrBlank()
    ) {
        return
    }

    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentTimeString = dateFormat.format(currentTime)
    // Verificar si la hora actual está entre las 06:00 y las 18:00
    if (currentTimeString >= "06:00" && currentTimeString <= "20:00") {
        LogUtils.insertLogAuditTrailUtils(
            auditTrailRepository,
            LocalDateTime.now().toString(),
            longitud,
            latitud,
            sharedPreferences.getUserId(),
            sharedPreferences.getUserName().toString(),
            0.0,
            porceBateria,
            tipoRegistro
        )
    }
}

private fun calcularDistancia(
    latitud: Double,
    longitud: Double,
    latitudInsert: Double,
    longitudInsert: Double
): Float {
    // Calcular la distancia en metros entre la ubicación actual y la ubicación anterior
    val distancia = FloatArray(1)
    Location.distanceBetween(
        latitudInsert,
        longitudInsert,
        latitud,
        longitud,
        distancia
    )
    return distancia[0]
}