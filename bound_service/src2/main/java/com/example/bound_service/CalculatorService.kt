package com.example.bound_service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.bound_service.MainActivity.Companion.TAG

class CalculatorService : Service() {
    inner class MyBinder: Binder(){
        fun getCalBoundService() : CalculatorService{
            Log.i(TAG, "getResult")
            return this@CalculatorService
        }
    }

    private var myBinder = MyBinder()

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind")
        return myBinder
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }

    fun calculate(n1: Int, n2: Int): Int {
        return n1 + n2
    }
}