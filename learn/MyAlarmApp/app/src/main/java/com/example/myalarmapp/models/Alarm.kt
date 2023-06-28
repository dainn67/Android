package com.example.myalarmapp.models

class Alarm(private val hour: Int, private val minute: Int, private val content: String, private val isRepeated: Boolean) {
    fun getHour() = hour
    fun getMinute() = minute
    fun getContent() = content
    fun getRepeatable() = isRepeated
}