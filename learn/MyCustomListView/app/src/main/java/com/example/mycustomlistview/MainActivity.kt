package com.example.mycustomlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private var userList = mutableListOf<User>()
    private lateinit var lvUsers: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUserList()

        val myUserAdapter = UserAdapter(this, R.layout.item_layout, userList)

        lvUsers = findViewById(R.id.lvUsers)
        lvUsers.adapter = myUserAdapter
    }

    private fun initUserList(){
        userList.add(User("Nguyen Nhu Dai", 18, "Male"))
        userList.add(User("Nguyen Thanh Dat", 18, "Male"))
        userList.add(User("Peter Parker", 20, "Male"))
        userList.add(User("Gwen Stacy", 16, "Female"))
        userList.add(User("Johnny Depth", 30, "Male"))
    }
}