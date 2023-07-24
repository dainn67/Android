package com.example.myalarmapp.models

import java.io.Serializable

class Alarm(
    private var hour: Int,
    private var minute: Int,
    private var content: String,
    private var isRepeated: Boolean,
    private var isOn: Boolean
) : Serializable {
    fun getHour() = hour
    fun getMinute() = minute
    fun getContent() = content
    fun getRepeat() = isRepeated
    fun getStatus() = isOn

    fun setHour(hour: Int) {
        this.hour = hour
    }
    fun setMinute(minute: Int) {
        this.minute = minute
    }
    fun setContent(content: String) {
        this.content = content
    }
    fun setRepeatable(isRepeated: Boolean) {
        this.isRepeated = isRepeated
    }
    fun setState(state: Boolean){
        this.isOn = state
    }
}