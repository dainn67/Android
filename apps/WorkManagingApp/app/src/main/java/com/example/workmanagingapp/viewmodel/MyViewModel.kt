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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("Range")
class MyViewModel(
    private val context: Context
) : ViewModel() {
    private val data = Data()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

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

    private var currentDay = dayList[0]

    //getters
    fun getAllWorkList() = allWorkList
    fun getCurrentWorkList() = currentWorkList
    fun getUpcomingWorkList() = upcomingWorkList
    fun getDayList() = dayList
    fun getCurrentDay() = currentDay

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
        fun displayTime(date: LocalDateTime): String {
            val displayHour = if (date.hour < 10) "0${date.hour}" else date.hour
            val displayMinute = if (date.minute < 10) "0${date.minute}" else date.minute

            return "$displayHour:$displayMinute"
        }

        fun displayTime(hour: Int, minute: Int): String {
            val displayHour = if (hour < 10) "0$hour" else hour
            val displayMinute = if (minute < 10) "0$minute" else minute

            return "$displayHour:$displayMinute"
        }

        fun displayDate(date: LocalDateTime): String {
            val displayDay = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else date.dayOfMonth
            val displayMonth =
                if (date.month.value < 10) "0${date.month.value}" else date.month.value

            return "$displayDay/$displayMonth/${date.year}"
        }
    }

    fun selectDayAndDisplayWork(position: Int) {
        currentDay = dayList[position]

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
            if (work.getTime().dayOfMonth == currentDay.getDate().dayOfMonth && work.getTime().month.value == currentDay.getDate().month.value)
                currentWorkList.add(work)
        }
        currentWorkListLiveData.value = currentWorkList
    }

    fun loadWorkList() {
//        addSampleWorkToSQLite()

        val projection = arrayOf(KEY_TITLE, KEY_TIME, KEY_CONTENT, KEY_STATUS)
        val sortOrder = KEY_TIME

        val tmpList = mutableListOf<Work>()
        val cursor =
            context.contentResolver.query(
                TABLE_URI,
                projection,
                null,
                null,
                sortOrder
            )

        //update the corresponding livedata
        allWorkList.clear()
        if (cursor?.moveToFirst() == true) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val timeString = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                val formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                var time = LocalDateTime.now()
                try {
                    time = LocalDateTime.parse(timeString, formatter)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
                val statusInt = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val status = (statusInt == 1)

                allWorkList.add(Work(title, time, content, status))

            } while (cursor.moveToNext())
            allWorkListLiveData.value = tmpList

            //strftime not working so I only query without whereClause
            filterWorks()
            indicateRedDot()
        } else {
            Log.i(TAG, "Not found")
        }
    }

    private fun filterWorks() {
        currentWorkList.clear()
        upcomingWorkList.clear()
        allWorkList.forEach { work ->
            val day = work.getTime().dayOfMonth
            val month = work.getTime().month.value

            if (day == currentDay.getDate().dayOfMonth && month == currentDay.getDate().month.value)
                currentWorkList.add(work)
            else if (month == LocalDateTime.now().month.value && day > LocalDateTime.now().dayOfMonth)
                upcomingWorkList.add(work)
        }

        currentWorkList.sortedWith(compareBy(Work::getTime).thenBy(Work::getTitle))
        upcomingWorkList.sortedWith(compareBy(Work::getTime).thenBy(Work::getTitle))
        currentWorkListLiveData.value = currentWorkList
        upcomingWorkListLiveData.value = upcomingWorkList
    }

    private fun indicateRedDot() {
        dayList.forEach { day ->
            allWorkList.forEach { work ->
                if (day.getDate().dayOfMonth == work.getTime().dayOfMonth && day.getDate().month.value == work.getTime().month.value) {
                    day.setHasWork(true)
                }
            }
        }

        dayListLiveData.value = dayList
    }

    fun addNewToList(work: Work) {
        //add to corresponding list
        Log.i(TAG, "add $work to list and DB")

        //add to database
        val values = ContentValues().apply {
            put(KEY_TITLE, work.getTitle())
            put(KEY_TIME, work.getTime().format(formatter))
            put(KEY_CONTENT, work.getContent())
            put(KEY_STATUS, 0)
        }

        context.contentResolver.insert(TABLE_URI, values)

        loadWorkList()
    }

    fun removeFromList(work: Work) {
        //delete from database
        val whereClause = "$KEY_TITLE = ? AND $KEY_CONTENT = ?"
        val whereArgs = arrayOf(work.getTitle(), work.getContent())

        context.contentResolver.delete(TABLE_URI, whereClause, whereArgs)

        //remove from allWorkList and corresponding list
        for (item in allWorkList)
            if (item.getTitle() == work.getTitle() && item.getContent() == work.getContent()) {
                allWorkList.remove(item)
                break
            }

        if (work.getTime().dayOfMonth == LocalDateTime.now().dayOfMonth && work.getTime().month == LocalDateTime.now().month) {
            currentWorkList.remove(work)
            currentWorkListLiveData.value = currentWorkList
        } else {
            upcomingWorkList.remove(work)
            upcomingWorkListLiveData.value = upcomingWorkList
        }
    }

    fun updateWorkInList(newWork: Work, work: Work) {
        val whereClause = "$KEY_TITLE = ? AND $KEY_CONTENT = ?"
        val whereArgs = arrayOf(work.getTitle(), work.getContent())

        val values = ContentValues().apply {
            put(KEY_TITLE, newWork.getTitle())
            put(KEY_CONTENT, newWork.getContent())
            put(KEY_TIME, newWork.getTime().format(formatter))
            put(KEY_STATUS, newWork.getStatus())
        }

        context.contentResolver.update(TABLE_URI, values, whereClause, whereArgs)

        loadWorkList()
    }

    private fun addSampleWorkToSQLite() {
        context.contentResolver.delete(TABLE_URI, null, null)

        var values = ContentValues().apply {
            put(KEY_TITLE, "Today")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            LocalDateTime.now().format(formatter)
            put(KEY_TIME, LocalDateTime.now().format(formatter))
            put(KEY_CONTENT, "Content today")
            put(KEY_STATUS, 0)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "Still Today")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = LocalDateTime.now().plusMinutes(30).format(formatter)
            put(KEY_TIME, time)
            put(KEY_CONTENT, "Still content today")
            put(KEY_STATUS, 1)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "Tomorrow")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = LocalDateTime.now().plusDays(1).format(formatter)
            put(KEY_TIME, time)
            put(KEY_CONTENT, "Content tomorrow")
            put(KEY_STATUS, 0)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "The day after tomorrow")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = LocalDateTime.now().plusDays(2).format(formatter)
            put(KEY_TIME, time)
            put(KEY_CONTENT, "Content the day after tomorrow")
            put(KEY_STATUS, 1)
        }
        context.contentResolver.insert(TABLE_URI, values)
        values = ContentValues().apply {
            put(KEY_TITLE, "Still Tomorrow")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = LocalDateTime.now().plusDays(1).format(formatter)
            put(KEY_TIME, time)
            put(KEY_CONTENT, "Content the day after tomorrow")
            put(KEY_STATUS, 1)
        }
        context.contentResolver.insert(TABLE_URI, values)
    }
}