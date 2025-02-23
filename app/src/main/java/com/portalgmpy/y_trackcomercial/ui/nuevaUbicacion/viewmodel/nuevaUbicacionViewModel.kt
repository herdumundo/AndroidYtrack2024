package com.portalgmpy.y_trackcomercial.ui.nuevaUbicacion.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgmpy.y_trackcomercial.data.model.models.OcrdItem
import com.portalgmpy.y_trackcomercial.repository.CustomerRepository
import com.portalgmpy.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
//import com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps.LocationService
import com.portalgmpy.y_trackcomercial.services.gps.servicioGMS.LocationCallBacks
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import com.portalgmpy.y_trackcomercial.services.gps.servicioGMS.LocationService
@HiltViewModel
class NuevaUbicacionViewModel @Inject constructor(
    private val context: Context,
    private val customerRepository: CustomerRepository,
    private val sharedPreferences: SharedPreferences,
    private val nuevaUbicacionRepository:NuevaUbicacionRepository,
    private val locationService: LocationService

) : ViewModel() {
   // private val  locationService: LocationService = LocationService()
    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    var latitud: MutableLiveData<Double> = _latitud

    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    val longitud: MutableLiveData<Double> = _longitud

    private val _ocrdName: MutableLiveData<String> = MutableLiveData()
    val ocrdName: MutableLiveData<String> = _ocrdName

    private val _idOcrd: MutableLiveData<String> = MutableLiveData()
    val idOcrd: MutableLiveData<String> = _idOcrd

    private val _showButtonPv = MutableLiveData<Boolean>()
    val showButtonPv: LiveData<Boolean> = _showButtonPv

    private val _buttonPvText = MutableLiveData<String>()
    val buttonPvText: LiveData<String> = _buttonPvText

    private val _buttonUbicacionActual = MutableLiveData<String>()
    val buttonUbicacionActual: LiveData<String> = _buttonUbicacionActual

    private val _showButtonSelectPv = MutableLiveData<Boolean>()
    val showButtonSelectPv: LiveData<Boolean> = _showButtonSelectPv
    private val _registrado = MutableLiveData<Boolean>()
    val registrado: LiveData<Boolean> = _registrado

    private val snackbarDuration = 3000L
    var permitirUbicacion=true

    private val _addressesList: MutableList<OcrdItem> = mutableListOf()
  //  private lateinit var locationListener: LocationListenerTest

    fun obtenerUbicacion(){

        if(permitirUbicacion)
        {
            _buttonUbicacionActual.value="Obteniendo ubicacion..."
            permitirUbicacion=false
            //   locationListener = LocationListenerTest()
            /*  val locationManager =  context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            obtenerUbicacionGPSActual(locationListener,context,locationManager) */
            viewModelScope.launch {
                locationService.startLocationUpdates()
                delay(5000)
                locationService.setLocationCallback(object : LocationCallBacks {
                    override fun onLocationUpdated(location: Location) {
                        // Actualiza la latitud y longitud con la ubicación actualizada
                        _longitud.value = location.longitude
                        _latitud.value = location.latitude
                    }
                })
                delay(2000)
                // 10 minutos en milisegundos
              /*  val resultLocation= locationService.getUserLocation(context)
                _longitud.value = resultLocation?.longitude ?: 0.0
                _latitud.value = resultLocation?.latitude ?: 0.0
               // Log.i("Ubicacion","Latitud "+resultLocation?.latitude.toString()+"  Longitud "+resultLocation?.longitude.toString())

                locationManager.removeUpdates(locationListener)*/
                locationService.stopLocationUpdates()
                permitirUbicacion=true
                _buttonUbicacionActual.value="Obtener ubicacion actual"
            }
            inicializarValores()
        }
    }

    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = customerRepository.getAddresses()
            withContext(Dispatchers.Main) {
                _addressesList.clear()
                _addressesList.addAll(addresses)
            }
        }
    }
    fun getStoredAddresses(): List<OcrdItem> {
        return _addressesList
    }

    fun setIdOcrd(idOcrd: String, ocrd: String) {
        _idOcrd.value = idOcrd
        _ocrdName.value = ocrd
        _showButtonPv.value = true
        _buttonPvText.value = ocrd
    }
    fun inicializarValores () {
        _buttonPvText.value = "Seleccionar punto de venta"
        _idOcrd.value = ""
        _ocrdName.value = ""
        _showButtonPv.value = false
    }

    fun registrar()
    {
         viewModelScope.launch {
            nuevaUbicacionRepository.insertNuevaUbicacion(UbicacionesNuevasEntity(
                id=System.currentTimeMillis(),
                idOcrd=idOcrd.value!!,
                idUsuario = sharedPreferences.getUserId(),
                createdAt = LocalDateTime.now().toString(),
                latitudPV = _latitud.value!!,
                longitudPV = _longitud.value!!,
                estado="P"
            ))
            inicializarValores()
            _registrado.value=true
            viewModelScope.launch {
                delay(snackbarDuration)
                _registrado.value = false
            }
        }

    }


}