package com.example.br_receive_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object{
        const val MY_KEY = "com.homesweethome.KEY"
    }

    private lateinit var explicitBroadcastReceiver: ExplicitBroadcastReceiver

    private lateinit var tvDisplay: TextView
    private lateinit var btnReceive: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
        btnReceive = findViewById(R.id.btnReceive)

        explicitBroadcastReceiver = ExplicitBroadcastReceiver()

        btnReceive.setOnClickListener{
            tvDisplay.text = explicitBroadcastReceiver.getText()
        }
    }
}