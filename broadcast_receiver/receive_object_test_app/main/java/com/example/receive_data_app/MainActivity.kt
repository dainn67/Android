package com.example.receive_data_app

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MyTag"
        const val MY_CUSTOM_ACTION= "com.homesweethome.CUSTOM_ACTION"
        const val MY_KEY = "com.homesweethome.KEY"
    }

    private lateinit var customBroadcastReceiver: CustomBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customBroadcastReceiver = CustomBroadcastReceiver()

        var tvDisplay = findViewById<TextView>(R.id.tvDisplay)
        var btnReceive = findViewById<Button>(R.id.btnReceive)

        btnReceive.setOnClickListener{
            //if data is sent is an object
//            var u1 = customBroadcastReceiver.getUserData()
//            tvDisplay.text = u1.name + " " + u1.age

            //if data is sent is a list of objects
            var resList: MutableList<User> = customBroadcastReceiver.getListOfUser()
            var displayContent = ""
            for(element in resList){
                displayContent += "${element.name} - ${element.age}\n"
            }


            tvDisplay.text = displayContent
        }
    }

    override fun onStart() {
        var intentFilter = IntentFilter(MY_CUSTOM_ACTION)
        registerReceiver(customBroadcastReceiver, intentFilter)
        Log.i(TAG, "registered")
        super.onStart()
    }

    override fun onDestroy() {
        unregisterReceiver(customBroadcastReceiver)
        super.onDestroy()
    }
}