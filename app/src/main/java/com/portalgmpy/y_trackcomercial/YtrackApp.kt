package com.portalgmpy.y_trackcomercial

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.portalgmpy.y_trackcomercial.services.alarmManager.MyAlarmReceiver
import com.portalgmpy.y_trackcomercial.services.alarmManager.scheduleExactAlarm
 import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YtrackApp:Application(){

    override fun onCreate() {
        super.onCreate()
        scheduleExactAlarm(this)

        if(Build.VERSION.SDK_INT<= Build.VERSION_CODES.Q){
            val channel = NotificationChannel("running_channel","Running Notifications", NotificationManager.IMPORTANCE_HIGH)
            val notificacionManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificacionManager.createNotificationChannel(channel)
        }


        // Llamar manualmente a MyAlarmReceiver para ejecutar la tarea inmediatamente al iniciar
        val receiver = MyAlarmReceiver()
        val intent = Intent(this, MyAlarmReceiver::class.java)
        receiver.onReceive(this, intent)
    }
    fun countRunningServices(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(Integer.MAX_VALUE)
        return runningServices.size
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}
