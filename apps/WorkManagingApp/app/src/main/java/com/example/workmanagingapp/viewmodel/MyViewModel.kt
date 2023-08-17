package com.example.workmanagingapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workmanagingapp.model.Constants
import com.example.workmanagingapp.model.Constants.Companion.KEY_CONTENT
import com.example.workmanagingapp.model.Constants.Companion.KEY_STATUS
import com.example.workmanagingapp.model.Constants.Companion.KEY_TIME
import com.example.workmanagingapp.model.Constants.Companion.KEY_TITLE
import com.example.workmanagingapp.model.Constants.Companion.TABLE_URI
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Data
import com.example.workmanagingapp.model.Day
import com.example.workmanagingapp.model.Work
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("Range")
class MyViewModel(
    private val context: Context
) : ViewModel() {
    private val data = Data()

    //get the list and use livedata to update
    private var dayList = data.getDayList()
    private var allWorkList = data.getWorkList()
    private var currentWorkList = mutableListOf<Work>()
    private var upcomingWorkList = mutableListOf<Work>()

    private var allWorkListLiveData = MutableLiveData<MutableList<Work>>()
    private var currentWorkListLiveData = MutableLiveData<MutableList<Work>>()
    private var upcomingWorkListLiveData = MutableLiveData<MutableList<Work>>()
    private var dayListLiveData = MutableLiveData<MutableList<Day>>()
    private var currentWorkTitleLiveData = MutableLiveData<String>()

    //getters
    fun getAllWorkList() = allWorkList
    fun getCurrentWorkList() = currentWorkList
    fun getUpcomingWorkList() = upcomingWorkList
    fun getDayList() = dayList

    fun setAllWorkList(list: MutableList<Work>) {
        allWorkList = list
    }

    fun setCurrentWorkList(list: MutableList<Work>) {
        currentWorkList = list
    }

    fun setUpcomingWorkList(list: MutableList<Work>) {
        upcomingWorkList = list
    }

    fun setDayList(list: MutableList<Day>) {
        dayList = list
    }

    //livedata
    fun getAllWorkListLiveData() = allWorkListLiveData
    fun getCurrentWorkListLiveData() = currentWorkListLiveData
    fun getUpcomingWorkListLiveData() = upcomingWorkListLiveData
    fun getDayListLiveData() = dayListLiveData
    fun getCurrentTitleLiveData() = currentWorkTitleLiveData

    //livedata needs to be initialized and assigned its value
    init {
        allWorkListLiveData = MutableLiveData()
        allWorkListLiveData.value = allWorkList

        currentWorkListLiveData = MutableLiveData()
        upcomingWorkListLiveData = MutableLiveData()

        dayListLiveData = MutableLiveData()
        dayListLiveData.value = dayList

        currentWorkTitleLiveData = MutableLiveData()
        currentWorkTitleLiveData.value =
            "TODAY'S WORK - ${LocalDate.now().dayOfMonth}/${LocalDate.now().month.value}"
    }

    companion object {
        fun displayTime(date: Date): String {
            val displayHour = if (date.hours < 10) "0${date.hours}" else date.hours
            val displayMinute = if (date.minutes < 10) " 0${date.minutes}" else date.minutes

            return "$displayHour:$displayMinute"
        }

        fun displayDate(date: Date): String {
            return "${date.date}/${date.month + 1}"
        }
    }

    fun selectDayAndDisplayWork(position: Int) {
        //set isSelected
        for (day in dayList) day.setIsSelected(false)
        dayList[position].setIsSelected(true)

        //display selected day's work
        if (position == 0) {
            currentWorkTitleLiveData.value =
                "TODAY'S WORK - ${dayList[position].getDateFormatted()}"
        } else {
            val currentDay = dayList[position]
            currentWorkTitleLiveData.value =
                "${currentDay.getDayOfWeekFull()} - ${currentDay.getDateFormatted()}"
        }

        //load the corresponding work list
        loadWorkList(dayList[position].getDate(), Constants.Companion.ViewDetailType.TODAY)


        dayListLiveData.value = dayList
    }

    fun loadWorkList(date: LocalDate, type: Constants.Companion.ViewDetailType) {
        Log.i(TAG, "Loading $type : ${date.dayOfMonth}/${date.month.value}")
        val projection = arrayOf(KEY_TITLE, KEY_TIME, KEY_CONTENT, KEY_STATUS)
        var selection = ""
        var selectionArgs = arrayOf<String>()
        when (type) {
            Constants.Companion.ViewDetailType.TODAY -> {
                selection = "strftime('%d', $KEY_TIME) = ? AND strftime('%m', $KEY_TIME) = ?"
                selectionArgs = arrayOf(date.dayOfMonth.toString(), date.month.value.toString())
            }

            Constants.Companion.ViewDetailType.UPCOMING -> {
                selection = "strftime('%d', $KEY_TIME) > ? AND strftime('%m', $KEY_TIME) > ?"
                selectionArgs = arrayOf(date.dayOfMonth.toString(), date.month.value.toString())
            }

            else -> {}
        }
        val sortOrder = KEY_TIME

        val tmpList = mutableListOf<Work>()
        val cursor =
            if (type != Constants.Companion.ViewDetailType.ALL) context.contentResolver.query(
                TABLE_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            else context.contentResolver.query(
                TABLE_URI,
                projection,
                null,
                null,
                sortOrder
            )
        if (cursor?.moveToFirst() == true) {
            Log.i(TAG, cursor.toString())
            do {
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val timeString = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                var time = Date()
                try {
                    time = sdf.parse(timeString)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
                val statusInt = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val status = (statusInt == 1)

                tmpList.add(Work(title, time, content, status))

            } while (cursor.moveToNext())

            //update the corresponding livedata
            when (type) {
                Constants.Companion.ViewDetailType.TODAY -> {
                    currentWorkList = tmpList
                    currentWorkListLiveData.value = tmpList
                }
                Constants.Companion.ViewDetailType.UPCOMING -> {
                    upcomingWorkList = tmpList
                    upcomingWorkListLiveData.value = tmpList
                }
                else -> {
                    allWorkList = tmpList
                    allWorkListLiveData.value = tmpList
                }
            }

            //strftime not working so I only query without whereClause
            filterWorks()
        } else {
            Log.i(TAG, "Not found")
        }
    }

    private fun filterWorks(){
        allWorkList.forEach {work ->
            val day = work.getTime().date
            val month = work.getTime().month + 1
            if(day == LocalDate.now().dayOfMonth && month == LocalDate.now().month.value)
                currentWorkList.add(work)
            else upcomingWorkList.add(work)
        }

        currentWorkListLiveData.value = currentWorkList
        upcomingWorkListLiveData.value = upcomingWorkList
    }
}