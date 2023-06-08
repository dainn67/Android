package com.example.foodbookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

enum class FragmentType{
    APPETIZER,
    MAIN_DISH,
    DESSERT
}

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MYTAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Hi")
    }
}