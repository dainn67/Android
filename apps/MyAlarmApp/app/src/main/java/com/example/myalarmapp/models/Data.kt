package com.example.myalarmapp.models

class Data {
    companion object{
        private var alarmList = mutableListOf<Alarm>()
        private var repeatAlarmList = mutableListOf<Alarm>()

        fun getAlarmList() = alarmList
        fun getRepeatList() = repeatAlarmList
    }
}