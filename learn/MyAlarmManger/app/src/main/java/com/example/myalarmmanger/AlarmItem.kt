package com.example.myalarmmanger

import java.time.LocalDateTime

class AlarmItem(val time: LocalDateTime, private val message: String) {
    fun getAlarmTime() = time
    fun getMessage() = message
}