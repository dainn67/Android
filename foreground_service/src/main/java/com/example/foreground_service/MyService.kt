package com.example.foreground_service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.foreground_service.MainActivity.Companion.CHANNEL_ID
import com.example.foreground_service.MainActivity.Companion.TAG

class MyService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    companion object {
        private const val ACTION_PAUSE = 1
        private const val ACTION_RESUME = 2
        private const val ACTION_CLEAR = 3
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "MyService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //get the song and pass into startMusic & sendNotification
        if (intent != null) {
            var bundle = intent.extras!!
            if(bundle != null){
                var song : Song = bundle.get("song1") as Song

                if(song != null) {
                    startMusic(song)
                    sendNotification(song)
                }
            }
        }
        return START_NOT_STICKY
    }

    //use mediaPlayer to start playing
    private fun startMusic(song: Song) {
        if(mediaPlayer == null) mediaPlayer = MediaPlayer.create(applicationContext, song.getImgRes())
        mediaPlayer?.start()
    }

    private fun handleActionMusic(action: Int){
        when (action){
            ACTION_PAUSE -> pauseMusic()
            ACTION_RESUME -> resumeMusic()
            else -> clearMusic()
        }
    }

    private fun clearMusic() {
        TODO("Not yet implemented")
    }

    private fun resumeMusic() {
        TODO("Not yet implemented")
    }

    private fun pauseMusic() {

    }

    private fun sendNotification(song: Song) {
        var intent = Intent(this, MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var bitMap = BitmapFactory.decodeResource(resources, song.getImgRes())
        var remoteView = RemoteViews(packageName, R.layout.layout_custom_notification)
        remoteView.setTextViewText(R.id.tv_title_song, song.getTitle())
        remoteView.setTextViewText(R.id.tv_singer_song, song.getSinger())
        remoteView.setImageViewBitmap(R.id.img_song, bitMap)
        remoteView.setImageViewResource(R.id.img_play_pause, R.drawable.pause)

        //build the notification using data received and add to Foreground
        var notification : Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_draft)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCustomContentView(remoteView)
            .setSound(null)
            .build()

        startForeground(1, notification)
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