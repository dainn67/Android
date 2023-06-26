package com.example.mycustomlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    companion object{
        private var userList = mutableListOf<User>()
        fun addToList(user: User) = userList.add(user)
    }
    private lateinit var lvUsers: ListView
    private lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUserList()

        val myUserAdapter = UserAdapter(this, R.layout.item_layout, userList)

        lvUsers = findViewById(R.id.lvUsers)
        lvUsers.adapter = myUserAdapter

        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val myDialog = CustomDialogFragment()
            myDialog.show(supportFragmentManager, "myDialog")
        }
    }

    private fun initUserList(){
        userList.add(User("Nguyen Nhu Dai", 18, Gender.MALE))
        userList.add(User("Nguyen Thanh Dat", 18, Gender.MALE))
        userList.add(User("Peter Porker", 20, Gender.MALE))
        userList.add(User("George Stacy", 16, Gender.FEMALE))
        userList.add(User("Johnny Shallow", 30, Gender.MALE))
    }
}