package com.example.workmanagingapp.model

import java.io.Serializable
import java.util.Date

class Work(
    private val title: String,
    private val time: Date,
    private val content: String,
    private val isDone: Boolean = false
):Serializable {
    fun getTitle() = title
    fun getTime() = time
    fun getContent() = content
    fun getStatus() = isDone

    override fun toString(): String {
        return "$title: ${time.hours}:${time.minutes} - $content - $isDone"
    }
}