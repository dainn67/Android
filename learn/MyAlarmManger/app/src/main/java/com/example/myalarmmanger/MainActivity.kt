package com.example.myalarmmanger

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MY-TAG"
    }

    private lateinit var btnSet: Button
    private lateinit var btnCancel: Button
    private lateinit var scheduler: AndroidAlarmScheduler
    private lateinit var aItem: AlarmItem
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSet = findViewById(R.id.btnSet)
        btnCancel = findViewById(R.id.btnCancel)

        scheduler = AndroidAlarmScheduler(this)

        btnSet.setOnClickListener {
            aItem = AlarmItem(
                LocalDateTime.now().plusSeconds((3).toLong()),
                "こにちわ、　初めまして　よろしくお願いします。"
            )
            Log.i(TAG, "btn clicked: ${aItem.getAlarmTime()}")
            scheduler.schedule(aItem)
        }

        btnCancel.setOnClickListener {
            if(aItem != null) scheduler.cancel(aItem)
        }
    }
}