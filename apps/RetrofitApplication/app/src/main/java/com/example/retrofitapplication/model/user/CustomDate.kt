package com.example.retrofitapplication.model.user

import java.io.Serializable

class CustomDate(
    private val date: String,
    private val age: Int
): Serializable {
}