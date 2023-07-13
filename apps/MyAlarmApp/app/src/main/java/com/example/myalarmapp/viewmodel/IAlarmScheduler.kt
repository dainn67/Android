package com.example.myalarmapp.viewmodel

import com.example.myalarmapp.models.Alarm

interface IAlarmScheduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}