package com.example.retrofitapplication.model

import com.example.retrofitapplication.model.user.User

class Data {
    private val list = mutableListOf<User>()

    fun getList() = list
}