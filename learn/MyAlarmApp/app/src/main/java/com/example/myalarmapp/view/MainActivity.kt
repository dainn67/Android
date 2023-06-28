package com.example.myalarmapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.lifecycle.ViewModel
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Data
import com.example.myalarmapp.viewmodel.MyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var lvAlarm: ListView
    private lateinit var btnAdd: FloatingActionButton

    //ViewModel
    private lateinit var myViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvAlarm = findViewById(R.id.lvAlarm)
        btnAdd = findViewById(R.id.btnAdd)

        //add button
        btnAdd.setOnClickListener {
            val dialog = AddAlarmDialogFragment(myViewModel)
            dialog.show(supportFragmentManager, "AddDialog")
        }

        //add sample data
        myViewModel = MyViewModel()
        (myViewModel as MyViewModel).addSampleAlarms()

        //display the list's items
        val alarmAdapter = AlarmAdapter(this, R.layout.alarm_item_layout, Data.getAlarmList())
        lvAlarm.adapter = alarmAdapter
    }


}