package com.example.myalarmapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Data

class MyViewModel: ViewModel() {
    private var list = Data.getAlarmList()

    fun addSampleAlarms() {
        list.add(Alarm(9, 30, "Breakfast"))
        list.add(Alarm(14, 0,"School"))
        list.add(Alarm(19, 45, "Learn Japanese"))
    }

    fun addToList(alarm: Alarm){
        list.add(alarm)
    }
}