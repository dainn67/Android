package com.example.backgroundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class BGService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    //background services
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val bundle = intent?.extras
        val song = bundle?.getSerializable("song") as Song

        handleMusic(song)

        return START_STICKY
    }

    private fun handleMusic(song: Song) {
        if (song != null) {
            mediaPlayer = MediaPlayer.create(this, song.getRes())
            mediaPlayer?.start()
        } else {
            Toast.makeText(this, "Play error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "BG Service destroyed", Toast.LENGTH_SHORT).show()
        mediaPlayer?.stop()
    }
}