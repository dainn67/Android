package com.example.myroomdbapp

import android.app.Application
import androidx.room.Room

class MyApp : Application() {
    companion object {
        const val DB_NAME = "user_database"
        var database: UserDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        if(database == null)
            synchronized(this){
                database = Room.databaseBuilder(applicationContext, UserDatabase::class.java, DB_NAME).build()
            }
    }
}