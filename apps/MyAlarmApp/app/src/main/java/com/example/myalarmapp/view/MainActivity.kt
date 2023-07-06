package com.example.myalarmapp.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.TAG
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "MainActivity onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val alarmAdapter = AlarmAdapter(this, R.layout.alarm_item_layout, myList, myViewModel, supportFragmentManager)
            lvAlarm.adapter = alarmAdapter
        }

        listLiveData.observe(this, myObserver)
    }
}