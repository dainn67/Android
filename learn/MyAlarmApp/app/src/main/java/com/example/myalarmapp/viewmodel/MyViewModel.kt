package com.example.myalarmapp.viewmodel

import android.app.AlarmManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import java.util.Calendar

class MyViewModel: ViewModel() {
    private var list = Data.getAlarmList()
    private var liveDataAlarmList = MutableLiveData<MutableList<Alarm>>()

    init {
        liveDataAlarmList = MutableLiveData()
        liveDataAlarmList.value = list
    }

    fun getLiveDataList() = liveDataAlarmList

    fun addSampleAlarms() {
        list.add(Alarm(9, 30, "Breakfast", false))
        list.add(Alarm(14, 0,"School", true))
        list.add(Alarm(19, 45, "Learn Japanese", false))
    }

    fun addToList(alarm: Alarm){
        list.add(alarm)

        //sort the list after adding new alarm
        list.sortWith(compareBy({ it.getHour() }, { it.getMinute() }))

        //update the live data
        liveDataAlarmList.value = list
    }

    fun removeFromList(position: Int){
        list.removeAt(position)
        liveDataAlarmList.value = list
    }

    fun editList(alarm: Alarm, position: Int){
        list[position] = alarm
        liveDataAlarmList.value = list
    }
}