package com.example.foodbookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodbookingapp.viewModel.FragmentsViewModel

enum class FragmentType{
    APPETIZER,
    MAIN_DISH,
    DESSERT
}

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MY-TAG"
        const val INITIAL_AMOUNT = 1

        val myViewModel = FragmentsViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}