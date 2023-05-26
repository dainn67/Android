package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MyTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = MyFragment1()
        val secondFragment = MyFragment2()

        var btnF1 = findViewById<Button>(R.id.btnF1)
        var btnF2 = findViewById<Button>(R.id.btnF2)

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.frag_container1, firstFragment)
//            addToBackStack("frag1")
//            commit()
//        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frag_container1, firstFragment, "frag1")
            addToBackStack(null)
        }.commit()

        btnF1.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frag_container1, firstFragment)
                addToBackStack(null)
                commit()
            }
        }

        btnF2.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                add(R.id.frag_container1, firstFragment)
                addToBackStack(null)
            }.commit()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.frag_container2, secondFragment)
                addToBackStack(null)
            }.commit()
        }


    }
}