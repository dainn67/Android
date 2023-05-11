package com.example.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ObjectBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null && intent.action == MainActivity.MY_CUSTOM_ACTION2){
            var resString = intent.getStringExtra(MainActivity.MY_KEY)
            Toast.makeText(context, resString, Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Send/Receive nothing", Toast.LENGTH_SHORT).show()
        }
    }
}