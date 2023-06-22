package com.example.myboundservice

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BService : Service() {
    inner class MyBinder : Binder() {
        fun getBService(): BService = this@BService
    }

    private val myBinder = MyBinder()

    override fun onBind(intent: Intent?): IBinder? = myBinder

    fun getSum(num1: Int, num2: Int): Int =  num1 + num2
}