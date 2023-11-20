package com.example.myroomdbapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var btn: FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userDao = MyApp.database?.userDao()

        btn = findViewById(R.id.btn)
        btn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                // Insert a user
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                userDao?.insertUser(User(name = "John Doe", email = "john@example.com", dob =  LocalDateTime.now().format(formatter)))

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
