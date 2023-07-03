package com.example.myalarmapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Data

class MyViewModel(
    private val context: Context
): ViewModel() {
    private var list = Data.getAlarmList()
    private var liveDataAlarmList = MutableLiveData<MutableList<Alarm>>()
    private val alarmScheduler = AlarmScheduler(context)

    init {
        liveDataAlarmList = MutableLiveData()
        liveDataAlarmList.value = list
    }

    fun getScheduler() = alarmScheduler

    fun getLiveDataList() = liveDataAlarmList

    fun addSampleAlarms() {
        list.add(Alarm(9, 30, "Breakfast", false, isOn = false))
        list.add(Alarm(14, 0,"School", true, isOn = true))
        list.add(Alarm(19, 45, "Learn Japanese", false, isOn = false))
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