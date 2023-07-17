package com.example.myalarmapp.viewmodel.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.ALARM_CODE
import com.example.myalarmapp.models.Constants.Companion.BROADCAST_ALARM_CODE
import com.example.myalarmapp.models.Constants.Companion.KILL_CODE
import com.example.myalarmapp.models.Constants.Companion.TO_KILL_CODE
import com.example.myalarmapp.viewmodel.AlarmScheduler
import com.example.myalarmapp.viewmodel.NotificationService

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras

        //receive alarm and kill action if exist
        val alarm = bundle?.getSerializable(ALARM_CODE) as Alarm?
        val kill = bundle?.getInt(KILL_CODE, -1)

        //intent and bundle to start notification
        val startNotiIntent = Intent(context, NotificationService::class.java)
        val startNotiBundle = Bundle()

        if (alarm != null) startNotiBundle.putSerializable(BROADCAST_ALARM_CODE, alarm)
        if (kill != null) startNotiBundle.putInt(TO_KILL_CODE, kill)

        startNotiIntent.putExtras(startNotiBundle)

        //start the notification
        context?.startService(startNotiIntent)

        //set the next alarm
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        var nextAlarmTime: Long = 0
        if (alarm != null) nextAlarmTime =
            AlarmScheduler.convertToMillis(alarm.getHour(), alarm.getMinute()) + 5000

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextAlarmTime,
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}