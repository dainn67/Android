package com.example.foreground_service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
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
    private var isPlaying = false
    private lateinit var mSong: Song

    companion object {
        private const val ACTION_PAUSE = 1
        private const val ACTION_RESUME = 2
        private const val ACTION_CLEAR = 3
        private var count = 1
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "MyService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(count == 1){
            var bundle = intent?.extras
            if (bundle != null) {
                var song: Song? = bundle.getSerializable("song1") as Song

                if (song != null) {
                    mSong = song
                    startMusic(song)
                    sendNotification(song)
                }
            }
            count = 2
        }

        if(count == 2){
            //get the action_number to handle click
            var actionMusic = intent?.getIntExtra("action_service_from_receiver", 0) !!
            handleActionMusic(actionMusic)
        }

        return START_NOT_STICKY
    }

    //use mediaPlayer to start playing
    private fun startMusic(song: Song) {
        if (mediaPlayer == null) mediaPlayer =
            MediaPlayer.create(applicationContext, song.getMusicRes())
        mediaPlayer?.start()
        isPlaying = true
    }

    private fun handleActionMusic(action: Int) {
        when (action) {
            ACTION_PAUSE -> pauseMusic()
            ACTION_RESUME -> resumeMusic()
            ACTION_CLEAR -> stopSelf()
        }
    }

    private fun resumeMusic() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
            sendNotification(mSong)
        }
    }

    private fun pauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            sendNotification(mSong)
        }
    }

    private fun sendNotification(song: Song) {
        var intent = Intent(this, MainActivity::class.java)
        var pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var bitMap = BitmapFactory.decodeResource(resources, song.getImgRes())
        var remoteView = RemoteViews(packageName, R.layout.layout_custom_notification)
        remoteView.setTextViewText(R.id.tv_title_song, song.getTitle())
        remoteView.setTextViewText(R.id.tv_singer_song, song.getSinger())
        remoteView.setImageViewBitmap(R.id.img_song, bitMap)
        remoteView.setImageViewResource(R.id.img_play_pause, R.drawable.pause)
        remoteView.setImageViewResource(R.id.img_clear, R.drawable.delete)

        if (isPlaying) {
            remoteView.setOnClickPendingIntent(R.id.img_play_pause, pending_play_pause_clear(this, ACTION_PAUSE))
            remoteView.setImageViewResource(R.id.img_play_pause, R.drawable.pause)
        }else {
            remoteView.setOnClickPendingIntent(R.id.img_play_pause, pending_play_pause_clear(this, ACTION_RESUME))
            remoteView.setImageViewResource(R.id.img_play_pause, R.drawable.play)
        }
        remoteView.setOnClickPendingIntent(R.id.img_clear, pending_play_pause_clear(this, ACTION_CLEAR))

        //build the notification using data received and add to Foreground
        var notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_draft)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCustomContentView(remoteView)
            .setSound(null)
            .build()

        startForeground(1, notification)
    }

    private fun pending_play_pause_clear(context: Context, action: Int): PendingIntent? {
        var intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("action", action)
        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
        count = 1
        Log.i(TAG, "DESTROYED")
        super.onDestroy()
    }
}