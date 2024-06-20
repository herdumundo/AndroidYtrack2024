package com.portalgmpy.y_trackcomercial.services.alarmManager

 import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import com.portalgmpy.y_trackcomercial.util.SharedData

class LocationUpdateManager(
    private val locationManager: LocationManager,
    private val locationListener: LocationListener,
) {
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 4 * 60 * 1000 // 3 minutos en milisegundos
    private val activeDuration: Long = 15 * 1000 // 15 segundos en milisegundos

    private val locationUpdateRunnable = object : Runnable {
        override fun run() {
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )

                val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lastKnownLocation?.let {
                    updateLocation(it)
                }

                // Detener las actualizaciones después de 15 segundos
                handler.postDelayed({
                    locationManager.removeUpdates(locationListener)
                }, activeDuration)

                // Reprogramar el próximo inicio de actualizaciones
                handler.postDelayed(this, updateInterval)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    fun startLocationUpdates() {
        handler.post(locationUpdateRunnable)
    }

    fun stopLocationUpdates() {
        handler.removeCallbacks(locationUpdateRunnable)
        locationManager.removeUpdates(locationListener)
    }

    private fun updateLocation(location: Location) {
        val sharedData = SharedData.getInstance()
        sharedData.latitudUsuarioActual.value = location.latitude
        sharedData.longitudUsuarioActual.value = location.longitude
    }
}

