package com.example.myalarmapp.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.models.Data
import java.time.LocalDateTime

class MyViewModel(
    private val context: Context
) : ViewModel() {
    private var list = Data.getAlarmList()
    private var repeatList = Data.getRepeatList()

    private var liveDataAlarmList = MutableLiveData<MutableList<Alarm>>()
    private val alarmScheduler = AlarmScheduler(context)

    init {
        liveDataAlarmList = MutableLiveData()
        liveDataAlarmList.value = list
    }

    fun getScheduler() = alarmScheduler

    fun getLiveDataList() = liveDataAlarmList
    fun getActiveList() = repeatList

    @RequiresApi(Build.VERSION_CODES.O)
    fun addSampleAlarms() {
        list.add(Alarm(9, 30, "Breakfast", false, isOn = false))
        list.add(Alarm(14, 0, "School", true, isOn = false))
        list.add(Alarm(19, 45, "Learn Japanese", false, isOn = false))

        //testing alarm 1 minute later
        val testAlarm = Alarm(
            LocalDateTime.now().hour,
            LocalDateTime.now().minute + 1,
            "Testing",
            true,
            isOn = true
        )
        list.add(testAlarm)
        repeatList.add(testAlarm)
        alarmScheduler.schedule(testAlarm)

        //debug
        Log.i(TAG, "Repeat list: ${repeatList.size}")
    }

    fun addToList(alarm: Alarm) {
        list.add(alarm)
        repeatList.add(alarm)
        Log.i(TAG, "Repeat list: ${repeatList.size}")

        //sort the list after adding new alarm
        list.sortWith(compareBy({ it.getHour() }, { it.getMinute() }))

        //update the live data
        liveDataAlarmList.value = list
    }

    fun removeFromList(position: Int) {
        alarmScheduler.cancel(list[position])
        list.removeAt(position)
        liveDataAlarmList.value = list
    }

    fun editList(alarm: Alarm, position: Int) {
        list[position] = alarm
        liveDataAlarmList.value = list
    }

    fun turnOff(alarm: Alarm) {
        Log.i(TAG, "Turn off ${alarm.getHour()}:${alarm.getMinute()}")
        for (refAlarm in list)
            if (refAlarm.getHour() == alarm.getHour() && refAlarm.getMinute() == alarm.getMinute())
                refAlarm.setState(false)

        //reset the livedata
        liveDataAlarmList.value = list
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setSound(null, null)
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}