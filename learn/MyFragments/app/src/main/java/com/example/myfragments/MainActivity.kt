package com.example.myfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd1: Button
    private lateinit var btnAdd2: Button

    private lateinit var myFrag1: Fragment
    private lateinit var myFrag2: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd1 = findViewById(R.id.add1)
        btnAdd2 = findViewById(R.id.add2)

        myFrag1 = FirstFragment()
        myFrag2 = FirstFragment()

        btnAdd1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragContainer1, myFrag1)
                commit()
            }
        }

        btnAdd2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragContainer2, myFrag2)
                commit()
            }
        }
    }
}