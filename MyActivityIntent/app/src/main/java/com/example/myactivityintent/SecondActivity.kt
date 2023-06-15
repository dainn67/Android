package com.example.myactivityintent

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bundle = intent.extras

        val tvName = findViewById<TextView>(R.id.tvName)
        tvName.text = bundle?.getString("name")

        val tvAge = findViewById<TextView>(R.id.tvAge)
        tvAge.text = bundle?.getInt("age").toString()
    }
}