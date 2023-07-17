package com.example.myalarmapp.viewmodel

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.ACTION_KILL
import com.example.myalarmapp.models.Constants.Companion.BROADCAST_ALARM_CODE
import com.example.myalarmapp.models.Constants.Companion.CHANNEL_ID
import com.example.myalarmapp.models.Constants.Companion.KILL_CODE
import com.example.myalarmapp.models.Constants.Companion.HOUR_CODE
import com.example.myalarmapp.models.Constants.Companion.MINUTE_CODE
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.models.Constants.Companion.TO_KILL_CODE
import com.example.myalarmapp.models.Constants.Companion.TURN_OFF_SWITCH_ALARM_CODE
import com.example.myalarmapp.models.Constants.Companion.TURN_OFF_SWITCH_CODE
import com.example.myalarmapp.view.MainActivity
import com.example.myalarmapp.viewmodel.receivers.AlarmReceiver

class NotificationService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private var alarm: Alarm? = null
    private var kill = -1

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand NotiService")

        val bundle = intent?.extras

        if (bundle != null) {
            //only get alarm from AlarmReceiver
            if(bundle.getSerializable(BROADCAST_ALARM_CODE) != null)
                alarm = bundle.getSerializable(BROADCAST_ALARM_CODE) as Alarm

            kill = bundle.getInt(TO_KILL_CODE, -1)

            //debug
            alarm?.let {
                Log.i(TAG, "Noti received: ${it.getHour()}:${it.getMinute()} - Kill: $kill")
            }
        }

        //check received intent to stop alarm
        if (kill != -1)
            stopAlarm(this)
        else {
            playAlarm(this)

            //intent to go to homepage
            val intent = Intent(this, MainActivity::class.java)
            val homePendingIntent = PendingIntent.getActivity(
                this,
                intent.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            //notification layout
            val remoteView = RemoteViews(packageName, R.layout.notification_layout)
            remoteView.setTextViewText(R.id.noti_content, alarm?.getContent() ?: "Error")
            remoteView.setTextViewText(R.id.noti_title, "Alarming at ${alarm?.getHour()}:${alarm?.getMinute()}")
            remoteView.setOnClickPendingIntent(R.id.noti_delete, pendingDeleteIntent(this))

            //create notification
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentIntent(homePendingIntent)    //when press the notification, go to home page
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCustomContentView(remoteView)
                .setSound(null)
                .build()

            startForeground(notification.hashCode(), notification)
        }

        return START_NOT_STICKY
    }

    private fun playAlarm(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.ringtone)
        mediaPlayer?.start()
        Log.i(TAG, "Alarm played")
    }

    private fun stopAlarm(context: Context) {
        mediaPlayer?.let {
            it.release()
        }
        stopSelf()
        stopForeground(true)

        //local broadcast to viewmodel to turn off the switch
        val turnOffIntent = Intent(TURN_OFF_SWITCH_CODE)
        val turnOffBundle = Bundle()
        turnOffBundle.putSerializable(TURN_OFF_SWITCH_ALARM_CODE, alarm)

        LocalBroadcastManager.getInstance(context).sendBroadcast(turnOffIntent)
        Log.i(TAG, "Alarm stopped")
    }

    private fun pendingDeleteIntent(context: Context): PendingIntent? {
        val deleteIntent = Intent(context, AlarmReceiver::class.java)

        val bundle = Bundle()
        bundle.putInt(KILL_CODE, ACTION_KILL)
//        bundle.putInt(HOUR_CODE, position)
        deleteIntent.putExtras(bundle)

        return PendingIntent.getBroadcast(
            context,
            deleteIntent.hashCode(),
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
        stopForeground(true)
    }
}