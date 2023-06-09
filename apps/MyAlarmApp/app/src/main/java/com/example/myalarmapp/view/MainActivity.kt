package com.example.myalarmapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants
import com.example.myalarmapp.models.Constants.Companion.HOUR_CODE
import com.example.myalarmapp.view.diaglogFragments.AddAlarmDialogFragment
import com.example.myalarmapp.viewmodel.MyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    //views
    private lateinit var lvAlarm: ListView
    private lateinit var btnAdd: FloatingActionButton

    //ViewModel
    private lateinit var myViewModel: MyViewModel

    //display list
    private lateinit var myList: MutableList<Alarm>

    //receive turnoff signal from notification
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            val hour = bundle?.getInt(HOUR_CODE, -1)
            val minute = bundle?.getInt(Constants.MINUTE_CODE, -1)
            if (hour != -1 && minute != -1) {
                myViewModel.turnOff(hour ?: -1, minute ?: -1)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //register broadcast to receive the turn off signal
        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver, IntentFilter(
                Constants.TURN_OFF_SWITCH_CODE
            )
        )

        btnAdd = findViewById(R.id.btnAdd)
        lvAlarm = findViewById(R.id.lvAlarm)

        //add button
        btnAdd.setOnClickListener {
            val dialog = AddAlarmDialogFragment(myViewModel)
            dialog.show(supportFragmentManager, "AddDialog")
        }

        //add sample data
        myViewModel = MyViewModel(this)
        myViewModel.createNotificationChannel()
        myViewModel.addSampleAlarms()

        //display to screen and listen to changes
        observeAlarmList()
    }

    private fun observeAlarmList() {
        //get live data & initially assign to myList
        val listLiveData = myViewModel.getLiveDataList()

        //observe changes
        val myObserver = Observer<MutableList<Alarm>> { newData ->
            myList = newData

            //set adapter
            val alarmAdapter = AlarmAdapter(
                this,
                R.layout.alarm_item_layout,
                myList,
                myViewModel,
                supportFragmentManager
            )
            lvAlarm.adapter = alarmAdapter
        }

        listLiveData.observe(this, myObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
}