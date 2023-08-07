package com.example.workmanagingapp.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.workmanagingapp.model.Constants.Companion.TAG
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class Data {
    private var workList = mutableListOf<Work>()
    private var dayList = mutableListOf<Day>()

    init {
        var localDate = LocalDate.now()
        var dayOfWeek = localDate.dayOfWeek

        for(i in 2..8){
            dayList.add(Day(dayOfWeek, localDate))
            dayOfWeek += 1
            localDate = localDate.plusDays(1)
        }

        dayList[1].setHasWork(true)
        dayList[3].setHasWork(true)
        dayList[4].setHasWork(true)
    }

    fun getWorkList() = workList
    fun getDayList() = dayList
}