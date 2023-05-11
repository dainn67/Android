package com.example.br_receive_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExplicitBroadcastReceiver: BroadcastReceiver() {
    private var resString: String = "Nothing"

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null){
            resString = intent.getStringExtra(MainActivity.MY_KEY).toString() ?: "Nothing yet"
            Toast.makeText(context, "FROM APP 2: $resString", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "FROM APP 2: intent is null", Toast.LENGTH_SHORT).show()
        }
    }

    fun getText(): String {return resString}
}