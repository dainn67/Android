package com.example.broadcast_receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExplicitBroadCastReceiver: BroadcastReceiver() {

    //Explicit BR don't need the ACTION code since the data is define to send directly to this BR
    //Must be registered in AndroidManifest
    //Can be used to receive data in different project, BUT MUST SET exported = true in RECEIVER in ANDROID MANIFEST

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null){
            var resString = intent.getStringExtra(MainActivity.MY_KEY) ?: "Nothing"
            Toast.makeText(context, resString, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Intent is null", Toast.LENGTH_SHORT).show()
        }
    }
}