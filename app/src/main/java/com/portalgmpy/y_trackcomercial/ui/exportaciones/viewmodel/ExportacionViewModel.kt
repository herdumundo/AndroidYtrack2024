package com.portalgmpy.y_trackcomercial.ui.exportaciones.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgmpy.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgmpy.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.portalgmpy.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.portalgmpy.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.portalgmpy.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.portalgmpy.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.portalgmpy.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.newPass.CountNewPassPendienteUseCase
import com.portalgmpy.y_trackcomercial.usecases.newPass.ExportarNewPassPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.newPass.GetNewPassPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesCancelacionesUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvCancelacionesPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.exports.ExportarOinvPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.imports.ImportarFacturasProcesadasSapUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.CountOinvNoProcesadasSapUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.oinv.queries.GetOinvPendientesExportarUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.vendedores.CountNroFacturaPendienteUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.vendedores.ExportarNroFacturaPendientesUseCase
import com.portalgmpy.y_trackcomercial.usecases.ventas.vendedores.GetNroFacturaPendienteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExportacionViewModel @Inject constructor(
    private val countCantidadPendientes: CountCantidadPendientes,
    private val countAuditTrailUseCase: CountAuditTrailUseCase,
    private val countLogPendientesUseCase: CountLogPendientesUseCase,
    private val countMovimientoUseCase: CountMovimientoUseCase,
    private val countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCase,
    private val countNewPassPendienteUseCase: CountNewPassPendienteUseCase,
    private val countOinvPendientesUseCase: CountOinvPendientesUseCase,
    private val countNroFacturaPendienteUseCase: CountNroFacturaPendienteUseCase,
    private val countOinvPendientesCancelacionesUseCase: CountOinvPendientesCancelacionesUseCase,
    private val countOinvNoProcesadasSapUseCase: CountOinvNoProcesadasSapUseCase,

    private val getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    private val getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase,
    private val getLogPendienteUseCase: GetLogPendienteUseCase,
    private val getMovimientoPendientesUseCase: GetMovimientoPendientesUseCase,
    private val getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCase,
    private val getNewPassPendientesUseCase: GetNewPassPendientesUseCase,
    private val getOinvPendientesExportarUseCase: GetOinvPendientesExportarUseCase,
    private val getNroFacturaPendienteUseCase: GetNroFacturaPendienteUseCase,


    private val enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    private val enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase,
    private val enviarLogPendientesUseCase: EnviarLogPendientesUseCase,
    private val enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCase,
    private val enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCase,
    private val enviarNewPassPendientesUseCase: ExportarNewPassPendientesUseCase,
    private val exportarOinvPendientesUseCase: ExportarOinvPendientesUseCase,
    private val exportarNroFacturaPendientesUseCase: ExportarNroFacturaPendientesUseCase,
    private val exportarOinvCancelacionesPendientesUseCase: ExportarOinvCancelacionesPendientesUseCase,
    private val importarFacturasProcesadasSapUseCase: ImportarFacturasProcesadasSapUseCase,

    ) : ViewModel() {

    private val _visitasCount: MutableLiveData<Int> = MutableLiveData()
    val visitasCount: LiveData<Int> = _visitasCount

    private val _auditTrailCount: MutableLiveData<Int> = MutableLiveData()
    val auditTrailCount: LiveData<Int> = _auditTrailCount

    private val _logCount: MutableLiveData<Int> = MutableLiveData()
    val logCount: LiveData<Int> = _logCount

    private val _movimientosCount: MutableLiveData<Int> = MutableLiveData()
    val movimientosCount: LiveData<Int> = _movimientosCount

    private val _ubicacionesNuevasCount: MutableLiveData<Int> = MutableLiveData()
    val ubicacionesNuevasCount: LiveData<Int> = _ubicacionesNuevasCount

    private val _newPassCount: MutableLiveData<Int> = MutableLiveData()
    val newPassCount: LiveData<Int> = _newPassCount

    private val _pendientesOinvCount: MutableLiveData<Int> = MutableLiveData()
    val pendientesOinvCount: LiveData<Int> = _pendientesOinvCount

    private val _nuevoNroFacturaCount: MutableLiveData<Int> = MutableLiveData()
    val nuevoNroFacturaCount: LiveData<Int> = _nuevoNroFacturaCount


    private val _loadingVisitas: MutableLiveData<Boolean> = MutableLiveData()
    val loadingVisitas: LiveData<Boolean> = _loadingVisitas

    private val _loadingNuevasUbicaciones: MutableLiveData<Boolean> = MutableLiveData()
    val loadingNuevasUbicaciones: LiveData<Boolean> = _loadingNuevasUbicaciones

    private val _loadingAuditTrail: MutableLiveData<Boolean> = MutableLiveData()
    val loadingAuditTrail: LiveData<Boolean> = _loadingAuditTrail

    private val _loadingLog: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLog: LiveData<Boolean> = _loadingLog

    private val _loadingFacturasNoProcesadasSap: MutableLiveData<Boolean> = MutableLiveData()
    val loadingFacturasNoProcesadasSap: LiveData<Boolean> = _loadingFacturasNoProcesadasSap

    private val _loadingMovimientos: MutableLiveData<Boolean> = MutableLiveData()
    val loadingMovimientos: LiveData<Boolean> = _loadingMovimientos

    private val _loadingNewPass: MutableLiveData<Boolean> = MutableLiveData()
    val loadingNewPass: LiveData<Boolean> = _loadingNewPass

    private val _loadingonuevoNroFacturaCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingonuevoNroFacturaCount: LiveData<Boolean> = _loadingonuevoNroFacturaCount

    private val _loadingAnulacionFacturaCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingAnulacionFacturaCount: LiveData<Boolean> = _loadingAnulacionFacturaCount

    private val _loadingOinv: MutableLiveData<Boolean> = MutableLiveData()
    val loadingOinv: LiveData<Boolean> = _loadingOinv

    private val _anulacionFacturaCount: MutableLiveData<Int> = MutableLiveData()
    val anulacionFacturaCount: LiveData<Int> = _anulacionFacturaCount

    private val _facturasNoProcesadasSapCount: MutableLiveData<Int> = MutableLiveData()
    val  facturasNoProcesadasSapCount: LiveData<Int> = _facturasNoProcesadasSapCount

    val batchSize = 5


    fun getTablasRegistradas(tipoRegistro: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val cantPendientes = when (tipoRegistro) {
                1 -> countCantidadPendientes.ContarCantidadPendientes()
                2 -> countAuditTrailUseCase.CountPendientesExportacion()
                3 -> countLogPendientesUseCase.CountPendientes()
                4 -> countMovimientoUseCase.CountPendientes()
                5 -> countUbicacionesNuevasPendientesUseCase.CountPendientes()
                6 -> countNewPassPendienteUseCase.CountPendientes()
                7 -> countOinvPendientesUseCase.CountPendientes()
                8 -> countNroFacturaPendienteUseCase.Obtener()
                9 -> countOinvPendientesCancelacionesUseCase.CountPendientes()
                10 -> countOinvNoProcesadasSapUseCase.CountPendientes()

                else -> 0 // O cualquier valor predeterminado si tipoRegistro no es 1 ni 2
            }
            withContext(Dispatchers.Main) {
                when (tipoRegistro) {
                    1 -> _visitasCount.value = cantPendientes
                    2 -> _auditTrailCount.value = cantPendientes
                    3 -> _logCount.value = cantPendientes
                    4 -> _movimientosCount.value = cantPendientes
                    5 -> _ubicacionesNuevasCount.value = cantPendientes
                    6 -> _newPassCount.value = cantPendientes
                    7 -> _pendientesOinvCount.value = cantPendientes
                    8 -> _nuevoNroFacturaCount.value = cantPendientes
                    9 -> _anulacionFacturaCount.value = cantPendientes
                    10 -> _facturasNoProcesadasSapCount.value = cantPendientes

                }
            }
        }
    }

    fun getTablasRegistradasTotal() {
        viewModelScope.launch(Dispatchers.IO) {
            val cantPendientesVisitas = countCantidadPendientes.ContarCantidadPendientes()
            val cantPendientesAuditTrail = countAuditTrailUseCase.CountPendientesExportacion()
            val cantPendientesLog = countLogPendientesUseCase.CountPendientes()
            val cantPendientesMovimientos = countMovimientoUseCase.CountPendientes()
            val cantUbicacionesNuevasPendientesUseCase = countUbicacionesNuevasPendientesUseCase.CountPendientes()
            val cantNuevoPassPendientesUseCase = countNewPassPendienteUseCase.CountPendientes()
            val countUseOinvPendientesCase = countOinvPendientesUseCase.CountPendientes()
            val countNroFacturaPendientesUseCase = countNroFacturaPendienteUseCase.Obtener()
            val countOinvPendientesCancelaciones = countOinvPendientesCancelacionesUseCase.CountPendientes()
            val countOinvNoProcesadasSapUseCase = countOinvNoProcesadasSapUseCase.CountPendientes()

            withContext(Dispatchers.Main) {
                _visitasCount.value = cantPendientesVisitas
                _auditTrailCount.value = cantPendientesAuditTrail
                _logCount.value = cantPendientesLog
                _movimientosCount.value = cantPendientesMovimientos
                _ubicacionesNuevasCount.value = cantUbicacionesNuevasPendientesUseCase
                _newPassCount.value = cantNuevoPassPendientesUseCase
                _pendientesOinvCount.value = countUseOinvPendientesCase
                _nuevoNroFacturaCount.value=countNroFacturaPendientesUseCase
                _anulacionFacturaCount.value=countOinvPendientesCancelaciones
                _facturasNoProcesadasSapCount.value=countOinvNoProcesadasSapUseCase
            }
        }
    }

    fun enviarPendientes(tipoRegistro: Int) {
        viewModelScope.launch {
            try {
                when (tipoRegistro) {
                    1 -> {
                        if (!_loadingVisitas.value!!) {
                            if (countCantidadPendientes.ContarCantidadPendientes() > 0) {
                                _loadingVisitas.value = true
                                val visitasPendientes =
                                    getVisitasPendientesUseCase.getVisitasPendientes()
                                val enviarVisitasRequest = EnviarVisitasRequest(visitasPendientes)
                                enviarVisitasPendientesUseCase.enviarVisitasPendientes(
                                    enviarVisitasRequest
                                )
                                _loadingVisitas.value = false
                            }
                        }
                    }

                    2 -> {
                        if (!loadingAuditTrail.value!!) {
                            if (countAuditTrailUseCase.CountPendientesExportacion() > 0) {
                                _loadingAuditTrail.value = true
                                val auditTrailPendientes = getAuditTrailPendienteUseCase.getAuditTrailPendientes()
                                val registrosPorBatches = auditTrailPendientes.chunked(batchSize)
                                registrosPorBatches.forEach { batch ->
                                    // Lógica para enviar el batch al servidor
                                    val enviarAuditTrailRequest = EnviarAuditoriaTrailRequest(batch)
                                    enviarAuditTrailPendientesUseCase.enviarAuditTrailPendientes(
                                        enviarAuditTrailRequest
                                    )
                                }

                                _loadingAuditTrail.value = false
                            }
                        }
                    }

                    3 -> {
                        if (!loadingLog.value!!) {
                            if (countLogPendientesUseCase.CountPendientes() > 0) {
                                _loadingLog.value = true
                                val auditLogPendientes =
                                    getLogPendienteUseCase.getAuditLogPendientes()
                                val registrosPorBatches = auditLogPendientes.chunked(batchSize)
                                registrosPorBatches.forEach { batch ->
                                    // Lógica para enviar el batch al servidor
                                    val enviarAuditLogRequest =
                                        EnviarLotesDeActividadesRequest(batch)
                                    enviarLogPendientesUseCase.enviarLogPendientes(
                                        enviarAuditLogRequest
                                    )
                                }

                                _loadingLog.value = false
                            }
                        }
                    }

                    4 -> {
                        if (!loadingMovimientos.value!!) {
                            if (countMovimientoUseCase.CountPendientes() > 0) {
                                _loadingMovimientos.value = true
                                val movimientosPendientes =
                                    getMovimientoPendientesUseCase.GetPendientes()
                                val enviarmovimientosRequest =
                                    EnviarLotesDeMovimientosRequest(movimientosPendientes)
                                enviarMovimientoPendientesUseCase.enviarPendientes(
                                    enviarmovimientosRequest
                                )
                                _loadingMovimientos.value = false
                            }
                        }
                    }

                    5 -> {
                        if (!loadingNuevasUbicaciones.value!!) {
                            if (countUbicacionesNuevasPendientesUseCase.CountPendientes() > 0) {
                                _loadingNuevasUbicaciones.value = true
                                val lotesPendientes =
                                    getNuevasUbicacionesPendientesUseCase.GetPendientes()
                                val enviarmovimientosRequest =
                                    EnviarLotesDeUbicacionesNuevasRequest(lotesPendientes)
                                enviarNuevasUbicacionesPendientesUseCase.enviarPendientes(
                                    enviarmovimientosRequest
                                )
                                _loadingNuevasUbicaciones.value = false
                            }
                        }
                    }

                    6 -> {
                        if (!loadingNewPass.value!!) {
                            if (countNewPassPendienteUseCase.CountPendientes() > 0) {
                                _loadingNewPass.value = true
                                val lotesPendientes = getNewPassPendientesUseCase.getPendientes()

                                enviarNewPassPendientesUseCase.enviarPendientes(
                                    lotesPendientes
                                )
                                _loadingNewPass.value = false
                            }
                        }
                    }

                    7 -> {
                        if (!loadingOinv.value!!) {
                            if (countOinvPendientesUseCase.CountPendientes() > 0) {
                                _loadingOinv.value = true
                                val lotesPendientes =  getOinvPendientesExportarUseCase.getPendientes()
                                exportarOinvPendientesUseCase.exportarDatos(lotesPendientes)
                                _loadingOinv.value = false
                            }
                        }
                    }

                    8 -> {
                        if (!loadingonuevoNroFacturaCount.value!!) {
                            if (countNroFacturaPendienteUseCase.Obtener() > 0) {
                                _loadingonuevoNroFacturaCount.value = true
                                val lotesPendientes =  getNroFacturaPendienteUseCase.Obtener()
                                exportarNroFacturaPendientesUseCase.exportarDatos(lotesPendientes)
                                _loadingonuevoNroFacturaCount.value = false
                            }
                        }
                    }

                    9 -> {
                        if (!loadingAnulacionFacturaCount.value!!) {
                            if (countOinvPendientesCancelacionesUseCase.CountPendientes() > 0) {
                                _loadingAnulacionFacturaCount.value = true
                                exportarOinvCancelacionesPendientesUseCase.exportarDatos()
                                _loadingAnulacionFacturaCount.value = false
                            }
                        }
                    }
                    10 -> {
                        if (!loadingFacturasNoProcesadasSap.value!!) {
                            if (countOinvNoProcesadasSapUseCase.CountPendientes() > 0) {
                                _loadingFacturasNoProcesadasSap.value = true
                                importarFacturasProcesadasSapUseCase.exportarDatos()
                                _loadingFacturasNoProcesadasSap.value = false
                            }
                        }
                    }

                }
                getTablasRegistradas(tipoRegistro)
            } catch (e: Exception) {
                Log.i("Mensaje", e.toString())
            }
        }
    }

    fun setFalseLoading() {
        _loadingFacturasNoProcesadasSap.value = false
        _loadingAuditTrail.value = false
        _loadingAnulacionFacturaCount.value = false
        _loadingVisitas.value = false
        _loadingLog.value = false
        _loadingMovimientos.value = false
        _loadingNuevasUbicaciones.value = false
        _loadingNewPass.value = false
        _loadingOinv.value = false
        _loadingonuevoNroFacturaCount.value = false

    }
}