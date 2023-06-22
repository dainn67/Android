package com.example.myforegroundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.myforegroundservice.Constants.Companion.ACTION_PLAY
import com.example.myforegroundservice.Constants.Companion.ACTION_STOP

class MainActivity : AppCompatActivity() {
    private lateinit var tvNowPlaying: TextView
    private lateinit var ivNowPlaying: ImageView

    private lateinit var btnPlayPause: Button
    private val mySong =
        Song(R.drawable.kiss_the_rain_icon, "Kiss the rain", "Yiruma", R.raw.kiss_the_rain)

    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        tvNowPlaying = findViewById(R.id.tvNowPlaying)
        ivNowPlaying = findViewById(R.id.imgNowPlaying)


        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnPlayPause.setOnClickListener {
            val intent = Intent(this, FGService::class.java)
            val bundle = Bundle()

            if (!isPlaying) {
                bundle.putSerializable("song", mySong)
                bundle.putInt("action", ACTION_PLAY)

                isPlaying = true
                findViewById<Button>(R.id.btnPlayPause).text = "Stop"
            } else {
                bundle.putInt("action", ACTION_STOP)

                isPlaying = false
                findViewById<Button>(R.id.btnPlayPause).text = "Play"
            }

            intent.putExtras(bundle)
            startService(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setSound(null, null)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}