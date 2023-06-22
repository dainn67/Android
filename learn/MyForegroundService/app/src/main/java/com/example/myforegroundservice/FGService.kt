package com.example.myforegroundservice

import android.app.NotificationManager
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
import com.example.myforegroundservice.Constants.Companion.ACTION_PAUSE
import com.example.myforegroundservice.Constants.Companion.ACTION_PLAY
import com.example.myforegroundservice.Constants.Companion.ACTION_RESUME
import com.example.myforegroundservice.Constants.Companion.ACTION_STOP
import com.example.myforegroundservice.Constants.Companion.CHANNEL_ID
import com.example.myforegroundservice.Constants.Companion.TAG

class FGService : Service() {
    private var action = 0
    private var isPlaying = false
    private var mySong: Song? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)    //stop the foreground
        stopSelf()                              //stop the service
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras
        if (bundle != null) action = bundle.getInt("action", 0)

        when(action){
            ACTION_PLAY -> {
                mySong = bundle?.getSerializable("song") as Song
                if (mySong != null) {
                    startMusic(mySong!!)
                    sendNotification()
                }
            }
            ACTION_STOP -> stopMusic()
            ACTION_PAUSE -> pauseMusic()
            ACTION_RESUME -> resumeMusic()
        }

        //if user interact with notification, get action here
        when(intent?.getIntExtra("broadcastAction", 0)){
            ACTION_PAUSE -> pauseMusic()
            ACTION_RESUME -> resumeMusic()
            ACTION_STOP -> stopMusic()
        }

        return START_STICKY
    }

    private fun startMusic(song: Song) {
        mediaPlayer = MediaPlayer.create(this, song.getRes())
        mediaPlayer?.start()
        isPlaying = true

        Log.i(TAG, "start")
    }

    private fun pauseMusic(){
        mediaPlayer?.pause()
        isPlaying = false
        sendNotification()

        Log.i(TAG, "pause")
    }

    private fun stopMusic() {
        mediaPlayer?.release()
        mediaPlayer?.let { mediaPlayer = null }
        isPlaying = false

        //remove the notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(1)

        stopForeground(true)
        stopSelf()

        Log.i(TAG, "stop")
    }

    private fun resumeMusic(){
        mediaPlayer?.start()
        isPlaying = true
        sendNotification()

        Log.i(TAG, "resume")
    }

    private fun sendNotification() {
        //intent: when click notification, move to main activity
        val intent = Intent(this, MainActivity::class.java)

        //pending intent: pause & stop from notification
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        //use RemoteViews to handle the notification (outside this activity)
        val remoteView = RemoteViews(packageName, R.layout.noti_layout)
        val bitMap = mySong?.getImg()?.let { BitmapFactory.decodeResource(resources, it) }
        remoteView.setImageViewBitmap(R.id.noti_img, bitMap)
        remoteView.setImageViewResource(R.id.noti_playPause, R.drawable.play_icon)
        remoteView.setImageViewResource(R.id.noti_delete, R.drawable.delete_icon)
        remoteView.setTextViewText(R.id.noti_title, mySong?.getTitle())
        remoteView.setTextViewText(R.id.noti_singer, mySong?.getSinger())

        //set pending intent here
        if(isPlaying){
            remoteView.setOnClickPendingIntent(R.id.noti_playPause, notificationAction(this, ACTION_PAUSE))
            remoteView.setImageViewResource(R.id.noti_playPause, R.drawable.pause_icon)
        }else{
            remoteView.setOnClickPendingIntent(R.id.noti_playPause, notificationAction(this, ACTION_RESUME))
            remoteView.setImageViewResource(R.id.noti_playPause, R.drawable.play_icon)
        }
        remoteView.setOnClickPendingIntent(R.id.noti_delete, notificationAction(this, ACTION_STOP))

        //create the notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.music_icon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCustomContentView(remoteView)
            .setSound(null)
            .build()
        startForeground(1, notification)
    }

    private fun notificationAction(context: Context, action: Int): PendingIntent? {
        //when click icon on notification, create a broadcast receiver the pass the action
        val intent = Intent(context, BReceiver::class.java)
        intent.putExtra("action", action)

        return PendingIntent.getBroadcast(applicationContext, action, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}