package com.example.myalarmapp.viewmodel

import com.example.myalarmapp.models.Alarm

interface InterfaceAlarmSchduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
}