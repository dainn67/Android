package com.example.workmanagingapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class Day(
    private val dayOfWeek: DayOfWeek,
    private val date: LocalDate,
    private var hasWork: Boolean = false
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayOfWeek(): String = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate() = "${date.dayOfMonth}/${date.month.value}"

    fun checkHasWork() = hasWork

    fun setHasWork(hasWork: Boolean) {this.hasWork = hasWork}

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toString(): String {
        return "$dayOfWeek: ${date.dayOfMonth}/${date.month}"
    }
}