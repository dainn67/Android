package com.example.myalarmapp.models

class Alarm(private var hour: Int, private var minute: Int, private var content: String, private var isRepeated: Boolean) {
    fun getHour() = hour
    fun getMinute() = minute
    fun getContent() = content
    fun getRepeatable() = isRepeated

    fun setHour(hour: Int) {this.hour = hour}
    fun setMinute(minute: Int) {this.minute = minute}
    fun setContent(content: String) {this.content = content}
    fun setRepeatable(isRepeated: Boolean) {this.isRepeated = isRepeated}
}