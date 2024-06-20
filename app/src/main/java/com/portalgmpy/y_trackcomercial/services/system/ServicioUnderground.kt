package com.portalgmpy.y_trackcomercial.services.system

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import com.portalgmpy.y_trackcomercial.BuildConfig
import com.portalgmpy.y_trackcomercial.MainActivity
import com.portalgmpy.y_trackcomercial.R
import com.portalgmpy.y_trackcomercial.repository.CustomerRepository
import com.portalgmpy.y_trackcomercial.repository.LotesListasRepository
import com.portalgmpy.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgmpy.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgmpy.y_trackcomercial.repository.OitmRepository
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgmpy.y_trackcomercial.services.battery.BatteryLevelReceiver
import com.portalgmpy.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgmpy.y_trackcomercial.services.datos_moviles.MobileDataReceiver
import com.portalgmpy.y_trackcomercial.services.gps.locationLocal.insertRoomLocation
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
import com.portalgmpy.y_trackcomercial.util.SharedData
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import com.portalgmpy.y_trackcomercial.util.logUtils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ServicioUnderground : Service() {
    @Inject
    lateinit var auditTrailRepository: AuditTrailRepository

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var logRepository: LogRepository

    @Inject
    lateinit var lotesListasRepository: LotesListasRepository

    @Inject
    lateinit var ocrdUbicacionesRepository: OcrdUbicacionesRepository

    @Inject
    lateinit var OcrdOitmRepository: OcrdOitmRepository

    @Inject
    lateinit var oitmRepository: OitmRepository

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Inject
    lateinit var countOinvPendientesUseCaseAlarmManager: CountOinvPendientesUseCaseAlarmManager

    @Inject
    lateinit var getOinvPendientesExportarUseCaseAlarmManager: GetOinvPendientesExportarUseCaseAlarmManager

    @Inject
    lateinit var exportarOinvPendientesUseCaseAlarmManager: ExportarOinvPendientesUseCaseAlarmManager
    @Inject
    lateinit var countNroFacturaPendienteUseCaseAlarmManager: CountNroFacturaPendienteUseCaseAlarmManager
    @Inject
    lateinit var getNroFacturaPendienteUseCaseAlarmManager: GetNroFacturaPendienteUseCaseAlarmManager
    @Inject
    lateinit var exportarNroFacturaPendientesUseCaseAlarmManager: ExportarNroFacturaPendientesUseCaseAlarmManager
    @Inject
    lateinit var countOinvNoProcesadasSapUseCaseAlarmManager: CountOinvNoProcesadasSapUseCaseAlarmManager
    @Inject
    lateinit var importarFacturasProcesadasSapUseCaseAlarmManager: ImportarFacturasProcesadasSapUseCaseAlarmManager
    @Inject
    lateinit var getVisitasPendientesUseCase: GetVisitasPendientesUseCaseAlarmManager
    @Inject
    lateinit var enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCaseAlarmManager
    @Inject
    lateinit var countCantidadPendientes: CountCantidadPendientesAlarmManager

    @Inject
    lateinit var pieSocketListener: PieSocketListener
    private var previousDataMobileState: Boolean? = null
    private lateinit var context: Context // Declaración de la propiedad de clase para el contexto
    private val gpsStateReceiver = GpsStateReceiver()
    private val mobileDataReceiver = MobileDataReceiver()
    val filter = IntentFilter("android.location.PROVIDERS_CHANGED")
    val sharedData = SharedData.getInstance()
    val batteryLevelReceiver = BatteryLevelReceiver()
    var latitudViejaWebSocket = 0.0
    var bateriaEach = 0 //SE USA PARA QUE AL ABRIR LA APP NUEVAMENTE NO INSERTE EL LOG DE BATERIA.

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        context = applicationContext // Asignación del contexto en onCreate
        registerReceiver(gpsStateReceiver, filter)
        val filterDataMobile = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mobileDataReceiver, filterDataMobile)
        //BATERIA SERVICIO
        context.registerReceiver(batteryLevelReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        sharedData.sharedBooleanLiveData.observeForever(gpsEnabledObserver)
        sharedData.sharedBooleanLiveDataMobile.observeForever(mobileDataEnabledObserver)
        sharedData.porcentajeBateria.observeForever(bateriaObserver)
        sharedData.latitudUsuarioActual.observeForever(latitudObserver)

        if (sharedPreferences.getUserId() > 0) {
            //SI EXISTE UN USUARIO INGRESADO PERMITIR.
            // Crea una instancia de PieSocketListener
            pieSocketListener = PieSocketListener(
                customerRepository,
                lotesListasRepository,
                ocrdUbicacionesRepository,
                OcrdOitmRepository,
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
            pieSocketListener.connectToServer(BuildConfig.BASE_URL_WEB_SOCKET)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val context = applicationContext
        //DESTRUIMOS EL OBSERVADORFOREVER PARA QUE NO HAGA INSERT DUPLICADOS.
        sharedData.sharedBooleanLiveData.removeObserver(gpsEnabledObserver)
        sharedData.sharedBooleanLiveDataMobile.removeObserver(mobileDataEnabledObserver)
        sharedData.porcentajeBateria.removeObserver(bateriaObserver)
        unregisterReceiver(mobileDataReceiver)
        context.unregisterReceiver(batteryLevelReceiver)
        unregisterReceiver(gpsStateReceiver)
        sharedData.latitudUsuarioActual.removeObserver(latitudObserver)
        pieSocketListener.closeWebSocket() // Agrega un método para cerrar la conexión WebSocket si no lo tienes ya implementado

        /*if (pieSocketListener != null) {
            pieSocketListener.closeWebSocket() // Agrega un método para cerrar la conexión WebSocket si no lo tienes ya implementado
        }*/
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
        //     return START_STICKY
    }

    private fun start() {
        startForegroundService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    enum class Actions {
        START, STOP
    }

    private fun startForegroundService() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val channelId = "servicio_channel"
                val channelName = "Servicio Channel"
                val notificationChannel =
                    NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            val notificationIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(notificationIntent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
            val notification = NotificationCompat.Builder(this, "servicio_channel")
                .setContentTitle("Ytrack activo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
            startForeground(1, notification)

        } catch (e: Exception) {
            Log.i("MensajeError", e.toString())
        }
    }

    private val latitudObserver = Observer<Double> { nuevaLatitud ->
        // Verificar si el WebSocket está abierto
        if (pieSocketListener.isConnected()) {
            val data = JSONObject()
            data.put("id", sharedPreferences.getUserId())
            data.put("latitud", nuevaLatitud)
            data.put("longitud", sharedData.longitudUsuarioActual.value ?: 0.0)
            val message = data.toString()
            pieSocketListener.enviarCoordenadas(message)
            latitudViejaWebSocket = nuevaLatitud
        }
    }
    private val bateriaObserver = Observer<Int> { cambioPorcentaje ->
        val context = applicationContext
        GlobalScope.launch {

            if (bateriaEach > 1) {
                Log.d("AlarmManagerCasoUso", "El contador de bateria $bateriaEach")

                if (sharedPreferences.getUserId() > 0) {
                    insertRoomLocation(
                        1.0, 1.0,
                        context, sharedPreferences, auditTrailRepository, "BATERIA"
                    )
                }
            }
            bateriaEach++
        }
    }
    private val gpsEnabledObserver = Observer<Boolean> { isGpsEnabled ->
        GlobalScope.launch {
            if (sharedPreferences.getUserId() > 0) {
                val porceBateria = getBatteryPercentage(this@ServicioUnderground)
                LogUtils.insertLog(
                    logRepository,
                    LocalDateTime.now().toString(),
                    "GPS $isGpsEnabled",
                    isGpsEnabled.toString(),
                    sharedPreferences.getUserId(),
                    sharedPreferences.getUserName()!!,
                    "SERVICIO SEGUNDO PLANO",
                    porceBateria
                )
            }
        }
    }
    private val mobileDataEnabledObserver = Observer<Boolean> { isDataMobileEnabled ->
        if (previousDataMobileState != isDataMobileEnabled) {
            if (sharedPreferences.getUserId() > 0) {
                GlobalScope.launch {
                    val porceBateria = getBatteryPercentage(this@ServicioUnderground)
                    LogUtils.insertLog(
                        logRepository,
                        LocalDateTime.now().toString(),
                        "Paquete de datos $isDataMobileEnabled",
                        isDataMobileEnabled.toString(),
                        sharedPreferences.getUserId(),
                        sharedPreferences.getUserName()!!,
                        "SERVICIO SEGUNDO PLANO",
                        porceBateria
                    )
                }
            }
            previousDataMobileState = isDataMobileEnabled
        }
    }
}