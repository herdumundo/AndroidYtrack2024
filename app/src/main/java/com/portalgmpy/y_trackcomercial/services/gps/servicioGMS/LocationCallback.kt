package com.portalgmpy.y_trackcomercial.services.gps.servicioGMS

import android.location.Location

interface LocationCallBacks {
    fun onLocationUpdated(location: Location)
}
