package com.example.foreground_service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.foreground_service.MainActivity.Companion.CHANNEL_ID
import com.example.foreground_service.MainActivity.Companion.TAG

class MyService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "MyService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            var bundle = intent.extras!!
            if(bundle != null){
                var song : Song = bundle.get("song1") as Song
                Log.i(TAG, song.getTitle())
                Log.i(TAG, song.getSinger())

                if(song != null) {
                    Log.i(TAG, "start music")
                    startMusic(song)
                    Log.i(TAG, "OK")
                    sendNotification(song)
                    Log.i(TAG, "GOOD")
                }
            }
        }
        return START_NOT_STICKY
    }

    private fun startMusic(song: Song) {
        if(mediaPlayer == null) {
            Log.i(TAG, "Create media player")
            mediaPlayer = MediaPlayer.create(applicationContext, song.getImgRes())
        }
        mediaPlayer?.start()
        Log.i(TAG, "start media player")
    }

    private fun sendNotification(song: Song) {
        Log.i(TAG, "0")
        var intent = Intent(this, MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        Log.i(TAG, "1")
        var bitMap = BitmapFactory.decodeResource(resources, song.getImgRes())
        Log.i(TAG, "TITLE: ${song.getTitle()}")
        Log.i(TAG, "SINGERL ${song.getSinger()}")
        var remoteView = RemoteViews(packageName, R.layout.layout_custom_notification)
        Log.i(TAG, "2")
        remoteView.setTextViewText(R.id.tv_title_song, song.getTitle())
        remoteView.setTextViewText(R.id.tv_singer_song, song.getSinger())
        remoteView.setImageViewBitmap(R.id.img_song, bitMap)
        remoteView.setImageViewResource(R.id.img_play_pause, R.drawable.pause)
        Log.i(TAG, "3")

        var notification : Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_draft)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCustomContentView(remoteView)
            .setSound(null)
            .build()
        Log.i(TAG, "4")

        startForeground(1, notification)
        Log.i(TAG, "Start foreground")
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroyed")
        if(mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
        super.onDestroy()
    }
}