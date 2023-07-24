package com.example.myalarmapp.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.DB_NAME
import com.example.myalarmapp.models.Constants.Companion.DB_VERSION
import com.example.myalarmapp.models.Constants.Companion.KEY_CONTENT
import com.example.myalarmapp.models.Constants.Companion.KEY_HOUR
import com.example.myalarmapp.models.Constants.Companion.KEY_ID
import com.example.myalarmapp.models.Constants.Companion.KEY_MINUTE
import com.example.myalarmapp.models.Constants.Companion.KEY_REPEAT
import com.example.myalarmapp.models.Constants.Companion.KEY_STATUS
import com.example.myalarmapp.models.Constants.Companion.TAG

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        createDB()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DB_NAME")
        onCreate(db)
    }

    fun createDB() {
        val createTableQuery =
            "CREATE TABLE $DB_NAME (" +
                    "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$KEY_HOUR INTEGER," +
                    "$KEY_MINUTE INTEGER," +
                    "$KEY_CONTENT TEXT," +
                    "$KEY_REPEAT INTEGER," +
                    "$KEY_STATUS INTEGER)"
        this.writableDatabase.execSQL(createTableQuery)
        Log.i(TAG, "Database created")
    }

    fun addAlarm(alarm: Alarm): Long {
        val db = this.writableDatabase
        val newRow = ContentValues().apply {
            put(KEY_HOUR, alarm.getHour())
            put(KEY_MINUTE, alarm.getMinute())
            put(KEY_CONTENT, alarm.getContent())
            put(KEY_REPEAT, if (alarm.getRepeat()) 1 else 0)
            put(KEY_STATUS, if (alarm.getStatus()) 1 else 0)
        }

        val id = db.insert(DB_NAME, null, newRow)
        db.close()

        return id
    }

    @SuppressLint("Range")
    fun getAllAlarms(list: MutableList<Alarm>) {
        val db = this.readableDatabase
        val selectAllQuery = "SELECT * FROM $DB_NAME"

        val cursor: Cursor? = db.rawQuery(selectAllQuery, null)

        if (cursor != null)
            if (cursor.moveToFirst())        //if there's at least 1 row in the result
                do {
                    val hour = cursor.getInt(cursor.getColumnIndex(KEY_HOUR))
                    val minute = cursor.getInt(cursor.getColumnIndex(KEY_MINUTE))
                    val content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
                    val repeat = cursor.getInt(cursor.getColumnIndex(KEY_REPEAT))
                    val status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))

                    val alarm = Alarm(hour, minute, content, repeat == 1, status == 1)
                    list.add(alarm)
                } while (cursor.moveToNext())

        cursor?.close()
        db.close()
    }

    fun clearAllAlarms() {
        val db = this.writableDatabase
        db.delete(DB_NAME, null, null)
        db.close()
    }

    //TODO: handle when user edit alarm or switch from AlarmAdapter
    fun editAlarm(oldAlarm: Alarm, newAlarm: Alarm){
        val db = this.writableDatabase
        val updateQuery =
            "UPDATE $DB_NAME " +
                    "SET $KEY_HOUR = ${newAlarm.getHour()}," +
                    "$KEY_MINUTE = ${newAlarm.getMinute()}," +
                    "$KEY_CONTENT = ${newAlarm.getContent()}," +
                    "$KEY_REPEAT = ${if(newAlarm.getRepeat()) 1 else 0}," +
                    "$KEY_STATUS = ${if(newAlarm.getStatus()) 1 else 0}" +
                    "WHERE $KEY_HOUR = ${oldAlarm.getHour()} AND $KEY_MINUTE = ${oldAlarm.getMinute()}"
        db.execSQL(updateQuery)

        Log.i(TAG, "Database updated")
        db.close()
    }
}