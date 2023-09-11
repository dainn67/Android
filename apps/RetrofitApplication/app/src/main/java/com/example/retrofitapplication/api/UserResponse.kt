package com.example.retrofitapplication.api

import com.example.retrofitapplication.model.User

data class UserResponse(
    val results: MutableList<User>,
    val info: Info
)

data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: Float
)