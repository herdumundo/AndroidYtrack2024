package com.portalgmpy.y_trackcomercial.services.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.portalgmpy.y_trackcomercial.services.system.ServicioUnderground

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            scheduleExactAlarm(context)
            Log.d("BootReceiver", "Device rebooted, alarm rescheduled.")
            // Iniciar el servicio en primer plano
            val serviceIntent = Intent(context, ServicioUnderground::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        }
    }
}


