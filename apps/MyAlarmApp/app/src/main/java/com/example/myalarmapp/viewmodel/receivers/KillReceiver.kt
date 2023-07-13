package com.example.myalarmapp.viewmodel.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.myalarmapp.models.Constants.Companion.KILL_CODE
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.models.Constants.Companion.TO_KILL_CODE
import com.example.myalarmapp.viewmodel.NotificationService

class KillReceiver: BroadcastReceiver() {
    private var kill = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras

        if(bundle != null) kill = bundle.getInt(KILL_CODE, 0)
        Log.i(TAG, "KillReceiver receive: $kill")

        val sendBackToNotiServiceIntent = Intent(context, NotificationService::class.java)
        val sendBackBundle = Bundle()
        sendBackBundle.putInt(TO_KILL_CODE, kill)
        sendBackToNotiServiceIntent.putExtras(sendBackBundle)

        context?.startService(sendBackToNotiServiceIntent)
    }
}