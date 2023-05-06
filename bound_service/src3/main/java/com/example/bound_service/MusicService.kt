package com.example.bound_service
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.example.bound_service.MainActivity.Companion.TAG

class MusicService : Service() {

    companion object{
        const val MSG_PLAY = 1
    }

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var myMessenger: Messenger

    inner class MyHandler(appContext: Context) : Handler(){
        private var appContext: Context? = null

        init {
            this.appContext = appContext
        }

        override fun handleMessage(msg: Message) {
            when(msg.what){
                MSG_PLAY -> playMusic()
                else -> super.handleMessage(msg)
            }
        }

    }

    private fun playMusic() {
        if(mediaPlayer == null) mediaPlayer = MediaPlayer.create(applicationContext, R.raw.i_luv_u)
        mediaPlayer?.start()
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind")
        myMessenger = Messenger(MyHandler(this))
        return myMessenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        if(mediaPlayer != null){
            mediaPlayer?.release()
            mediaPlayer = null
        }
        super.onDestroy()
    }
}