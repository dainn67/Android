package com.example.ot1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.ot1.Constants.CHANNEL_ID
import com.example.ot1.Constants.MUSIC_NOTIFICATION_ID

class MusicActivity : Service() {
    private lateinit var musicPlayer: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotiChannel()
    }

    private fun initMusic() {
        musicPlayer = MediaPlayer.create(this, R.raw.my_music)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(100F, 100F)
    }

    private fun createNotiChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "My service channel", NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        if(musicPlayer.isPlaying)
            musicPlayer.stop()
        else{
            musicPlayer.start()
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = Notification.Builder(this, CHANNEL_ID).setContentText("Music player")
            .setSmallIcon(R.drawable.ic_launcher_foreground).setContentIntent(pendingIntent).build()
        startForeground(MUSIC_NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()

        musicPlayer.stop()
        musicPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}