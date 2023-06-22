package com.example.myforegroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getIntExtra("action", 0)

        //after get the action code, send it back to mainActivity
        val broadcastIntent = Intent(context, FGService::class.java)
        broadcastIntent.putExtra("broadcastAction", action)

        context?.startService(broadcastIntent)
    }
}