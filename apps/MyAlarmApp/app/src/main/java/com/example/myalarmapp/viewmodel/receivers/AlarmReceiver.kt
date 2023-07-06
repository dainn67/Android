package com.example.myalarmapp.viewmodel.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.viewmodel.NotificationService

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        val alarm = bundle?.getSerializable("alarm") as Alarm

        Toast.makeText(
            context,
            "Received: ${alarm.getHour()}: ${alarm.getMinute()} - ${alarm.getContent()} - ${alarm.getRepeatable()}",
            Toast.LENGTH_SHORT
        ).show()

        val startNotiIntent = Intent(context, NotificationService::class.java)
        val startNotiBundle = Bundle()
        startNotiBundle.putSerializable("alarm_from_broadcast", alarm)
        startNotiIntent.putExtras(startNotiBundle)

        context?.startService(startNotiIntent)

        Log.i(TAG, "Receiver send: ${alarm.getHour()}:${alarm.getMinute()}")
    }
}