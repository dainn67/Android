package com.example.myalarmapp.viewmodel.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.viewmodel.NotificationService

class KillReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val kill = intent?.getBooleanExtra("kill", false)
        Log.i(TAG, "KillReceiver receive: $kill")

        val sendBackToNotiServiceIntent = Intent(context, NotificationService::class.java)
        sendBackToNotiServiceIntent.putExtra("toKill", kill)
        context?.startService(sendBackToNotiServiceIntent)
    }
}