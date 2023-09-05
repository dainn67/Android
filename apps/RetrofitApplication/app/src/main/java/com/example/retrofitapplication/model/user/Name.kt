package com.example.retrofitapplication.model.user

import java.io.Serializable

class Name(
    private val title: String,
    private val first: String,
    private val last: String
): Serializable {
}