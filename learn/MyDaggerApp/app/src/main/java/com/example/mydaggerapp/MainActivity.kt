package com.example.mydaggerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val myComponent: MyComponent by lazy {
        DaggerMyComponent.builder().build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myString = myComponent.getString()
        // Now, myString contains "Hello, Dagger!"
        Log.i("aaa", myString)
    }
}