package com.example.myalarmapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.models.Constants.Companion.TURN_OFF_SWITCH_ALARM_CODE
//import com.example.myalarmapp.models.Constants.Companion.TURN_OFF_SWITCH_CODE
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
    private val turnoffBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            val receivedAlarm: Alarm? = bundle?.getSerializable(TURN_OFF_SWITCH_ALARM_CODE) as Alarm?
            if (receivedAlarm != null) {
                Log.i(TAG, "Main received kill command")
                myViewModel.turnOff(receivedAlarm)
                myViewModel.getScheduler().cancel(receivedAlarm)
            }else{
                Log.i(TAG, "Main: Alarm not received")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //register broadcast to receive the turn off signal
        LocalBroadcastManager.getInstance(this).registerReceiver(
            turnoffBroadcastReceiver, IntentFilter(Constants.TURN_OFF_SWITCH_CODE)
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(turnoffBroadcastReceiver)
    }
}