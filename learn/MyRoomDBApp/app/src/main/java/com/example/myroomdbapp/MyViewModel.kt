package com.example.myroomdbapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MyViewModel(private val context: Context): ViewModel(){

//    fun addUser(user: User){
//        val db = UserDB.getDatabase(context)
//
//        db?.userDAO?.insertUser(user)
//
//        val list =  db?.userDAO?.getUserList()
//        Log.i("aaa", list?.size.toString())
//    }
}