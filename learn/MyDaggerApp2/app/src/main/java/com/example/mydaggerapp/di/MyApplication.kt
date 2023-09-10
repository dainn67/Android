package com.example.mydaggerapp.di

import android.app.Application
import android.util.Log
import com.example.mydaggerapp.model.Constants.Companion.TAG

class MyApplication : Application() {
    val myAppComponent: AppComponent by lazy {
        Log.i(TAG, "appComponent")
        DaggerAppComponent.builder()
            .myModule(MyModule)
            .build()
    }
}