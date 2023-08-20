package com.example.workmanagingapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.workmanagingapp.model.Constants.Companion.DatabaseContract.DATABASE_NAME
import com.example.workmanagingapp.model.Constants.Companion.TABLE_NAME
import com.example.workmanagingapp.model.Constants.Companion.DatabaseContract.DATABASE_VERSION
import com.example.workmanagingapp.model.Constants.Companion.KEY_CONTENT
import com.example.workmanagingapp.model.Constants.Companion.KEY_ID
import com.example.workmanagingapp.model.Constants.Companion.KEY_STATUS
import com.example.workmanagingapp.model.Constants.Companion.KEY_TIME
import com.example.workmanagingapp.model.Constants.Companion.KEY_TITLE
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class DatabaseHelper(
    private val context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val rDB = this.readableDatabase
    private val wDB = this.writableDatabase

    fun getRDB(): SQLiteDatabase = rDB
    fun getWDB(): SQLiteDatabase = wDB

    init {
        val createQuery = "" +
                "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$KEY_ID INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "$KEY_TITLE TEXT," +
                "$KEY_TIME DATETIME," +
                "$KEY_CONTENT TEXT," +
                "$KEY_STATUS INTEGER )"
        wDB.execSQL(createQuery)
    }

    //Only called when database doesn't exist and need initial create
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "" +
                "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$KEY_ID INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "$KEY_TITLE TEXT," +
                "$KEY_TIME DATETIME," +
                "$KEY_CONTENT TEXT," +
                "$KEY_STATUS INTEGER )"
        wDB.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun removeDatabase() {
        for (name in context.databaseList())
            Log.i(TAG, name)
        Log.i(TAG, "--------$TABLE_NAME----------")
        this.wDB.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        for (name in context.databaseList())
            Log.i(TAG, name)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun loadList(list: MutableList<Work>) {
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"

        val cursor = rDB.rawQuery(selectAllQuery, null)

        if (cursor.moveToFirst())
            do {
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val timeString = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                var time = LocalDateTime.now()
                try {
                    time = LocalDateTime.parse(timeString, formatter)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
                val statusInt = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val status = (statusInt == 1)

                val work = Work(title, time, content, status)
                list.add(work)
            } while (cursor.moveToNext())
    }
}