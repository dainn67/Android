package com.example.workmanagingapp.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
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

    //livedata
    private var dayListLiveData = MutableLiveData<MutableList<Day>>()
    private var currentWorkTitleLiveData = MutableLiveData<String>()
    private var addNewDateTVLiveData = MutableLiveData<String>()
    private var addNewTimeTVLiveData = MutableLiveData<String>()

    private var allWorkListLiveData = MutableLiveData<MutableList<Work>>()
    private var currentWorkListLiveData = MutableLiveData<MutableList<Work>>()
    private var upcomingWorkListLiveData = MutableLiveData<MutableList<Work>>()

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
    fun getAddNewDateTVLiveData() = addNewDateTVLiveData
    fun getAddNewTimeTVLiveData() = addNewTimeTVLiveData

    //livedata needs to be initialized and assigned its value
    init {
        dayListLiveData = MutableLiveData()
        dayListLiveData.value = dayList

        allWorkListLiveData = MutableLiveData()
        allWorkListLiveData.value = allWorkList

        currentWorkListLiveData = MutableLiveData()
        upcomingWorkListLiveData = MutableLiveData()

        currentWorkTitleLiveData = MutableLiveData()
        currentWorkTitleLiveData.value =
            "TODAY'S WORK - ${LocalDate.now().dayOfMonth}/${LocalDate.now().month.value}"

        addNewDateTVLiveData = MutableLiveData()
        addNewDateTVLiveData.value =
            "Date: ${if (LocalDate.now().dayOfMonth < 10) "0${LocalDate.now().dayOfMonth}" else LocalDate.now().dayOfMonth}/${if (LocalDate.now().month.value < 10) "0${LocalDate.now().month.value}" else LocalDate.now().month.value}/${LocalDate.now().year}"
    }

    companion object {
        fun displayTime(date: Date): String {
            val displayHour = if (date.hours < 10) "0${date.hours}" else date.hours
            val displayMinute = if (date.minutes < 10) " 0${date.minutes}" else date.minutes

            return "$displayHour:$displayMinute"
        }

        fun displayTime(hour: Int, minute: Int): String {
            val displayHour = if (hour < 10) "0$hour" else hour
            val displayMinute = if (minute < 10) " 0$minute" else minute

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
        dayListLiveData.value = dayList

        //display corresponding title
        val currentDay = dayList[position]
        if (position == 0)
            currentWorkTitleLiveData.value =
                "TODAY'S WORK - ${currentDay.getDateFormatted()}"
        else
            currentWorkTitleLiveData.value =
                "${currentDay.getDayOfWeekFull()} - ${currentDay.getDateFormatted()}"

        //display the corresponding work list
        currentWorkList.clear()
        allWorkList.forEach { work ->
            if (work.getTime().date == currentDay.getDate().dayOfMonth && work.getTime().month + 1 == currentDay.getDate().month.value)
                currentWorkList.add(work)
        }
        currentWorkListLiveData.value = currentWorkList
    }

    fun loadWorkList(date: LocalDate, type: Constants.Companion.ViewDetailType) {
        addSampleWorkToSQLite()

        Log.i(TAG, "Loading $type : ${date.dayOfMonth}/${date.month.value}")
        val projection = arrayOf(KEY_TITLE, KEY_TIME, KEY_CONTENT, KEY_STATUS)
        var selection: String?
        var selectionArgs: Array<String>?
        when (type) {
            Constants.Companion.ViewDetailType.TODAY -> {
                selection = "strftime('%d', $KEY_TIME) = ? AND strftime('%m', $KEY_TIME) = ?"
                selectionArgs = arrayOf(date.dayOfMonth.toString(), date.month.value.toString())
            }

            Constants.Companion.ViewDetailType.UPCOMING -> {
                selection = "strftime('%d', $KEY_TIME) > ? AND strftime('%m', $KEY_TIME) > ?"
                selectionArgs = arrayOf(date.dayOfMonth.toString(), date.month.value.toString())
            }

            else -> {
                selection = null
                selectionArgs = null
            }
        }
        val sortOrder = KEY_TIME

        val tmpList = mutableListOf<Work>()
        val cursor =
            context.contentResolver.query(
                TABLE_URI,
                projection,
                selection,
                selectionArgs,
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
            indicateRedDot()
        } else {
            Log.i(TAG, "Not found")
        }
    }

    private fun filterWorks() {
        allWorkList.forEach { work ->
            val day = work.getTime().date
            val month = work.getTime().month + 1
            if (day == LocalDate.now().dayOfMonth && month == LocalDate.now().month.value)
                currentWorkList.add(work)
            else upcomingWorkList.add(work)
        }

        currentWorkListLiveData.value = currentWorkList
        upcomingWorkListLiveData.value = upcomingWorkList
    }

    private fun indicateRedDot() {
        dayList.forEach { day ->
            allWorkList.forEach { work ->
                if (day.getDate().dayOfMonth == work.getTime().date && day.getDate().month.value == work.getTime().month + 1) {
                    day.setHasWork(true)
                }
            }
        }

        dayListLiveData.value = dayList
    }

    private fun addSampleWorkToSQLite() {
        context.contentResolver.delete(TABLE_URI, null, null)

        var values = ContentValues().apply {
            put(KEY_TITLE, "Today")
            put(KEY_TIME, "2023-08-18 09:09:09")
            put(KEY_CONTENT, "Content today")
            put(KEY_STATUS, 0)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "Still Today")
            put(KEY_TIME, "2023-08-18 10:09:09")
            put(KEY_CONTENT, "Still content today")
            put(KEY_STATUS, 1)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "Tomorrow")
            put(KEY_TIME, "2023-08-19 09:09:09")
            put(KEY_CONTENT, "Content tomorrow")
            put(KEY_STATUS, 0)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "The day after tomorrow")
            put(KEY_TIME, "2023-08-20 09:09:09")
            put(KEY_CONTENT, "Content the day after tomorrow")
            put(KEY_STATUS, 1)
        }
        context.contentResolver.insert(TABLE_URI, values)
    }
}