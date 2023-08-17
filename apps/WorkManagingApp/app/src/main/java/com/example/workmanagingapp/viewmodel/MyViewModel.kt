package com.example.workmanagingapp.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class MyViewModel(
    private val context: Context
) : ViewModel() {
    private val data = Data()

    //get the list and use livedata to update
    private var workList = data.getWorkList()
    private var dayList = data.getDayList()

    private var workListLiveData = MutableLiveData<MutableList<Work>>()
    private var dayListLiveData = MutableLiveData<MutableList<Day>>()

    //getters
    fun getWorkList() = workList
    fun getDayList() = dayList

    fun setWorkList(list: MutableList<Work>) {
        workList = list
    }

    fun setDayList(list: MutableList<Day>) {
        dayList = list
    }

    //livedata
    fun getWorkListLiveData() = workListLiveData
    fun getDayListLiveData() = dayListLiveData

    //livedata needs to be initialized and assigned its value
    init {
        workListLiveData = MutableLiveData()
        workListLiveData.value = workList

        dayListLiveData = MutableLiveData()
        dayListLiveData.value = dayList
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

    fun selectDay(position: Int) {
        //set isSelected
        for (day in getDayList()) day.setIsSelected(false)
        getDayList()[position].setIsSelected(true)
    }

    @SuppressLint("Range")
    fun loadWorkList() {

        //add sample data
        /*
        var tmpList = mutableListOf<Work>()
        tmpList.add(Work("家に帰る", Date(), "帰ろう"))
        tmpList.add(Work("勉強する", Date(), "じゃ、初めまして"))
        tmpList.add(Work("友達を会う", Date(), "こうえんにいこう"))
        tmpList.add(Work("しけんがある", Date(), "じゅんびして"))
        tmpList.add(Work("総つぎょする", Date(), "大学を出る"))

        for(work in tmpList){
            val values = ContentValues().apply {
                put(KEY_TITLE, work.getTitle())
                put(KEY_TIME, SimpleDateFormat(work.formatTime(), Locale.getDefault()).format(Date()))
                put(KEY_CONTENT, work.getContent())
                put(KEY_STATUS, work.getStatus())
            }
            context.contentResolver.insert(TABLE_URI, values)
        }

        //Clear the table here
        context.contentResolver.delete(TABLE_URI, null, null)
        */

        val cursor = context.contentResolver.query(TABLE_URI, null, null, null, null)
        if (cursor?.moveToFirst() == true)
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

                val work = Work(title, time, content, status)
                workList.add(work)
            } while (cursor.moveToNext())

        workListLiveData.value = workList
    }
}