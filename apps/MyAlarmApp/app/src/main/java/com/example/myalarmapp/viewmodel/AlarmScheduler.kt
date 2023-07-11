package com.example.myalarmapp.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.viewmodel.receivers.AlarmReceiver
import java.util.Calendar

class AlarmScheduler(
    private val context: Context
): InterfaceAlarmSchduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedule(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable("alarm", alarm)
        intent.putExtras(bundle)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            convertToMillis(alarm.getHour(), alarm.getMinute()),
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        Log.i(TAG, "Scheduled at ${alarm.getHour()}:${alarm.getMinute()}")
    }

    override fun cancel(alarm: Alarm) {
        //same pending intent when setting the alarm
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable("alarm", alarm)
        intent.putExtras(bundle)

        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.i(TAG, "Cancelled ${alarm.getHour()}:${alarm.getMinute()}")
    }

    private fun convertToMillis(hour: Int, minute: Int): Long{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }
}