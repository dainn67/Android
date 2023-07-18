package com.example.myalarmapp.models

class Data {
    companion object{
        private var alarmList = mutableListOf<Alarm>()
        private var activeAlarmList = mutableListOf<Alarm>()

        fun getAlarmList() = alarmList
        fun getActiveList() = activeAlarmList
    }
}