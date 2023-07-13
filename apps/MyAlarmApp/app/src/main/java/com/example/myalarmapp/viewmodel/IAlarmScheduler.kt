package com.example.myalarmapp.viewmodel

import com.example.myalarmapp.models.Alarm

interface IAlarmScheduler {
    fun schedule(alarm: Alarm, position: Int)
    fun cancel(alarm: Alarm)
}