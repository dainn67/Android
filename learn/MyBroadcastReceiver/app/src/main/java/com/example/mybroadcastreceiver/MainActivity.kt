package com.example.mybroadcastreceiver

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var networkBroadcastReceiver: NetworkBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkBroadcastReceiver = NetworkBroadcastReceiver()

        //specify what the BroadcastReceiver may receive & register
        var intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkBroadcastReceiver, intentFilter)
    }

    //static broadcast receiver:
        //declared in Manifest file
        //no need to register & unregister
        //receive broadcast even when the app isn't running
        // -> for system-level events
    //dynamic broadcast receiver:
        //declared as a class or inner class
        //register using registerReceiver() method
        //only receive when the app is running
        // -> more flexible
}