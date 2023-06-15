package com.example.backgroundservice

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object{
        val mySong = Song("Kiss the rain", R.raw.kiss_the_rain)
    }

    private lateinit var tvNowPlaying: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvNowPlaying = findViewById(R.id.tvNowPlaying)

        var isPlaying = false
        val serviceIntent = Intent(this, BGService::class.java)

        val btnPlayPause = findViewById<Button>(R.id.bntPlayPause)
        btnPlayPause.setOnClickListener {
            if (!isPlaying) {
                btnPlayPause.text = "Stop"

                val bundle = Bundle()
                bundle.putSerializable("song", mySong)
                bundle.putInt("action", 1)
                serviceIntent.putExtras(bundle)

                tvNowPlaying.visibility = View.VISIBLE
                tvNowPlaying.text = "Now playing: ${mySong.getName()}"

                startService(serviceIntent)
            } else {
                btnPlayPause.text = "Play"
                tvNowPlaying.visibility = View.GONE
                stopService(serviceIntent)
            }

            isPlaying = !isPlaying
        }

    }
}