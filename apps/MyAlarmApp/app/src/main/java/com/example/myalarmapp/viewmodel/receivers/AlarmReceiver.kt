package com.example.myalarmapp.viewmodel.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.ALARM_CODE
import com.example.myalarmapp.models.Constants.Companion.BROADCAST_ALARM_CODE
import com.example.myalarmapp.models.Constants.Companion.KILL_CODE
import com.example.myalarmapp.models.Constants.Companion.TAG
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

        if(alarm != null)
            Log.i(TAG, "Alarm receiver: ${AlarmScheduler.hashcodeAlarm(alarm)}")
        if(kill == 1)
            Log.i(TAG, "Alarm receiver: KILL")

        //intent and bundle to start notification
        val sendToNotificationIntent = Intent(context, NotificationService::class.java)
        val sendToNotificationBundle = Bundle()

        if (kill == 1)
            sendToNotificationBundle.putInt(TO_KILL_CODE, kill)
        if (alarm != null)
            sendToNotificationBundle.putSerializable(BROADCAST_ALARM_CODE, alarm)

        sendToNotificationIntent.putExtras(sendToNotificationBundle)

        //start the notification
        context?.startService(sendToNotificationIntent)

        //set the next alarm
        if(kill != 1){
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            var nextAlarmTime: Long = 0
            if (alarm != null) nextAlarmTime = AlarmScheduler.convertToMillis(alarm.getHour(), alarm.getMinute())

            if(alarm != null){
                Log.i(TAG, "Set new repeat alarm: ${AlarmScheduler.hashcodeAlarm(alarm)}")

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextAlarmTime,
                    PendingIntent.getBroadcast(
                        context,
                        AlarmScheduler.hashcodeAlarm(alarm),
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
            }
        }
    }
}