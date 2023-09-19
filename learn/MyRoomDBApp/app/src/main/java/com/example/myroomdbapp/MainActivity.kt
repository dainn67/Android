package com.example.myroomdbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
//    private lateinit var viewModel: MyViewModel
    private lateinit var btn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userDao = MyApp.database?.userDao()

        btn = findViewById(R.id.btn)
        btn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                // Insert a user
                userDao?.insertUser(User(name = "John Doe", email = "john@example.com"))

                // Retrieve all users
                val users = userDao?.getAllUsers()

                // Use the list of users (e.g., update UI)
                withContext(Dispatchers.Main) {
                    // Update UI here
                    Log.i("aaa", users?.size.toString())
                }
            }
        }
    }
}
