package com.example.workmanagingapp.model

class Data {
    private var workList = mutableListOf<Work>()
    private val dayList = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")

    fun getWorkList() = workList
    fun getDayList() = dayList
}