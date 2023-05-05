package com.example.bound_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MYTAG"
    }

    private var myMusicBoundService: MusicBoundService = MusicBoundService()
    private var isConnected: Boolean = false

    lateinit var startBtn: Button
    lateinit var stopBtn: Button

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            var myBinder: MusicBoundService.MyBinder = iBinder as MusicBoundService.MyBinder
            myMusicBoundService = myBinder.getMusicBoundService()
            myMusicBoundService.startMusic()
            isConnected = true
        }

        //this is called when the service is forced to be terminated
        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)

        startBtn.setOnClickListener{
            startTheService()
            Log.i(TAG, "press start button")
        }
        stopBtn.setOnClickListener{
            stopTheService()
            Log.i(TAG, "press start stop")
        }
    }

    private fun startTheService() {
        var intent = Intent(this, MusicBoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    //this is called when the service stop on purpose
    private fun stopTheService() {
        if(isConnected){
            unbindService(serviceConnection)
            isConnected = false
        }
    }
}