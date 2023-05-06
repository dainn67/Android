package com.example.bound_service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MYTAG"
    }

    //create a messenger to send message
    private var myMessenger: Messenger? = null
    private var isConnected = false

    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            myMessenger = Messenger(iBinder)
            isConnected = true

            //after assigning is finished, play music
            sendMessageToPlayMusic()
            Log.i(TAG, "onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myMessenger = null
            isConnected = false
            Log.i(TAG, "onServiceDisconnected")
        }

    }

    private fun sendMessageToPlayMusic() {
        Log.i(TAG, "sendMessageToPlayMusic")

        //create and send the message
        var message = Message.obtain(null, MusicService.MSG_PLAY, 0, 0)
        myMessenger?.send(message)
    }

    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)


        startBtn.setOnClickListener{
            Log.i(TAG, "press start")
            startTheService()
        }
        stopBtn.setOnClickListener{
            stopTheService()
            Log.i(TAG, "press stop")


        }
    }

    private fun startTheService() {
        var intent = Intent(this, MusicService::class.java)
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