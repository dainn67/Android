package com.example.bound_service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.bound_service.MainActivity.Companion.TAG

class MusicBoundService : Service() {
    inner class MyBinder : Binder() {
        fun getMusicBoundService(): MusicBoundService {
            Log.i(TAG, "getMusicBoundService")
            return this@MusicBoundService
        }
    }

    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind")
        return myBinder
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
        if (mediaPlayer != null){
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    fun startMusic(){
        Log.i(TAG, "start music")
        if(mediaPlayer == null) mediaPlayer = MediaPlayer.create(applicationContext, R.raw.i_luv_u)
        mediaPlayer?.start()
    }
}