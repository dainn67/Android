package com.example.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.broadcast_receiver.MainActivity.Companion.TAG

class CustomBroadcastReceiver: BroadcastReceiver() {
    private var res : String = "nothing yet"

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null){
            if (intent.action == MainActivity.MY_CUSTOM_ACTION){
                res = intent.getStringExtra("text").toString()
                Log.i(TAG, res)
            }
        }else{
            Log.i(TAG, "intent onReceive is null")
        }
    }

    fun returnRes(): String{
        return res;
    }
}