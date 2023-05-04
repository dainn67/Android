package com.example.foreground_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.foreground_service.MainActivity.Companion.TAG

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var actionMusic = intent?.getIntExtra("action", 0)
        var intentService = Intent(context, MyService::class.java)
        Log.i(TAG, "In MyReceiver: $actionMusic")
        intentService.putExtra("action_service_from_receiver", actionMusic)

        //foreground service will not be recreated, only onStartCommand but this time with the new intentService
        context?.startService(intentService)
    }
}