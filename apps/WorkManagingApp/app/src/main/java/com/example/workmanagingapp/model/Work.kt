package com.example.workmanagingapp.model

import java.util.Date

class Work(
    private val title: String,
    private val time: Date,
    private val content: String,
    private val isDone: Boolean
) {
    fun getTitle() = title
    fun getTime() = time
    fun getContent() = content
    fun getStatus() = isDone
}