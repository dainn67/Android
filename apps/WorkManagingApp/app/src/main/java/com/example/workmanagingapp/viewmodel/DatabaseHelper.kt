package com.example.workmanagingapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.workmanagingapp.model.Constants.Companion.DB_NAME
import com.example.workmanagingapp.model.Constants.Companion.DB_VERSION
import com.example.workmanagingapp.model.Constants.Companion.KEY_CONTENT
import com.example.workmanagingapp.model.Constants.Companion.KEY_ID
import com.example.workmanagingapp.model.Constants.Companion.KEY_STATUS
import com.example.workmanagingapp.model.Constants.Companion.KEY_TIME
import com.example.workmanagingapp.model.Constants.Companion.KEY_TITLE
import com.example.workmanagingapp.model.Work
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val rDB = this.readableDatabase
    private val wRB = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        createDB()
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DB_NAME")
        onCreate(db)
    }

    fun createDB(){
        val createQuery = "" +
                "CREATE TABLE $DB_NAME (" +
                "$KEY_ID INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "$KEY_TITLE TEXT," +
                "$KEY_TIME DATETIME," +
                "$KEY_CONTENT TEXT," +
                "$KEY_STATUS INTEGER )"
        wRB.execSQL(createQuery)
    }

    @SuppressLint("Range")
    fun loadList(list: MutableList<Work>){
        val selectAllQuery = "SELECT * FROM $DB_NAME"

        val cursor = rDB.rawQuery(selectAllQuery, null)

        if(cursor.moveToFirst())
            do {
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val timeString = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                var time: Date = Date()
                try {
                    time = sdf.parse(timeString)
                }catch (e: ParseException){
                    e.printStackTrace()
                }
                val content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
                val statusInt = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val status = (statusInt == 1)

                val work = Work(title, time, content, status)
                list.add(work)
            }while (cursor.moveToNext())
    }
}