package com.example.myalarmapp.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myalarmapp.models.Alarm

class AlarmReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarm = intent?.getSerializableExtra("alarm") as Alarm
        Toast.makeText(context, "${alarm.getHour()}: ${alarm.getMinute()} - ${alarm.getContent()} - ${alarm.getRepeatable()}", Toast.LENGTH_SHORT).show()
    }
}