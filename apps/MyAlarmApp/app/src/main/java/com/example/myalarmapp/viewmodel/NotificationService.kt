package com.example.myalarmapp.viewmodel

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.CHANNEL_ID
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.view.MainActivity
import com.example.myalarmapp.viewmodel.receivers.KillReceiver

class NotificationService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var alarm: Alarm
    private var kill = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras

        if (bundle != null) {
            alarm = bundle.getSerializable("alarm_from_broadcast") as Alarm
            kill = bundle.getBoolean("toKillOrNotToKill", false)
            Log.i(TAG, "Noti received: ${alarm.getHour()}:${alarm.getMinute()} - Kill: $kill")
        }

        //check received intent to stop alarm
        if (kill)
            stopAlarm()
        else {
            playAlarm(this)

            //intent to go to homepage
            val intent = Intent(this, MainActivity::class.java)
            val homePendingIntent = PendingIntent.getActivity(
                this,
                intent.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val remoteView = RemoteViews(packageName, R.layout.notification_layout)
            remoteView.setTextViewText(R.id.noti_content, alarm.getContent())
            remoteView.setOnClickPendingIntent(R.id.noti_delete, pendingDeleteIntent(this))

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSound(null)
                .setCustomContentView(remoteView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(homePendingIntent)    //when press the notification, go to home page
                .build()

            startForeground(1, notification)
        }

        return START_STICKY
    }

    private fun playAlarm(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.ringtone)
        mediaPlayer?.start()
        Log.i(TAG, "Alarm played")
    }

    private fun stopAlarm() {
        mediaPlayer?.let {
            it.release()
        }
        Log.i(TAG, "Alarm stopped")
    }

    private fun pendingDeleteIntent(context: Context): PendingIntent? {
        val intent = Intent(context, KillReceiver::class.java)
        intent.putExtra("kill", true)

        return PendingIntent.getActivity(
            context,
            intent.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
        stopForeground(true)
    }
}