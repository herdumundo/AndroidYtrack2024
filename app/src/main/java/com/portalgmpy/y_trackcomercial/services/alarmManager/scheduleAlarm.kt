package com.portalgmpy.y_trackcomercial.services.alarmManager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.concurrent.TimeUnit

@SuppressLint("ScheduleExactAlarm")
fun scheduleExactAlarm(context: Context) {
         val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MyAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val triggerTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            alarmIntent
        )

        Log.d("AlarmManager", "Exact alarm scheduled to trigger in 15 minutes")
    }



