package com.example.foreground_service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnStartStop : Button
    lateinit var edtDataIntent : EditText
    lateinit var channel: NotificationChannel
    lateinit var manager: NotificationManager

    var started = false

    companion object{
        const val CHANNEL_ID = "MY_CHANNEL_ID"
        const val TAG = "MY_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createChannelNotification()

        btnStartStop = findViewById(R.id.startStop)

        //set on click button to start/stop music
        //note: when restart app, started is false by default, so if music is playing -> need to click 2 times
        btnStartStop.setOnClickListener {
            if (!started) {
                Log.i(TAG, "button start")
                Toast.makeText(this, "Start service", Toast.LENGTH_SHORT).show()
                startStringService()
                started = !started
            }
            else {
                Log.i(TAG, "button stop")
                Toast.makeText(this, "Stop service", Toast.LENGTH_SHORT).show()
                stopStringService()
                started = !started
            }
        }
    }


    //from Android O, we need to create a notification channel to display notification
    private fun createChannelNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel = NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_HIGH)
            channel.setSound(null, null)
            channel.description = "My channel description"

            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if(manager != null) {
                manager.createNotificationChannel(channel)
                Log.i(TAG, "Channel created")
            }else{
                Log.i(TAG, "Channel create failed")
            }
        }
    }

    private fun startStringService() {
        var song = Song("Rain", "Lofi", R.raw.music, R.drawable.music_note)

        intent = Intent(this, MyService::class.java)
//        intent.putExtra("intent_data", edtDataIntent.text.toString().trim())
        var bundle = Bundle()
        bundle.putSerializable("song1", song)
        intent.putExtras(bundle)
        startService(intent)
    }

    private fun stopStringService() {
        intent = Intent(this, MyService::class.java)
        stopService(intent)
    }
}