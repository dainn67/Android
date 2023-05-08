package com.example.broadcast_receiver

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MyTag"
    }

    //create a broadcast receiver
    private lateinit var myBroadcastReceiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myBroadcastReceiver = MyBroadcastReceiver()
    }

    override fun onStart() {
        super.onStart()

        //register the receiver using intentFilter to check CONNECTIVITY_ACTION
        var intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myBroadcastReceiver, intentFilter)
        Log.i(TAG, "onStart - registered")
    }

    override fun onStop() {
        unregisterReceiver(myBroadcastReceiver)
        Log.i(TAG, "onStop - unregistered")
        super.onStop()
    }

    override fun onRestart() {
        Log.i(TAG, "onRestart")
        super.onRestart()
    }
}