package com.example.myroomdbapp

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MyApp : Application() {
    companion object {
        const val DB_NAME = "user_database"
        var database: UserDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        if(database == null) {
            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // Write SQL statements to migrate data
                    database.execSQL("ALTER TABLE users ADD COLUMN dob TEXT")
                }
            }
            synchronized(this) {
                database =
                    Room.databaseBuilder(applicationContext, UserDatabase::class.java, DB_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build()
            }
        }
    }
}