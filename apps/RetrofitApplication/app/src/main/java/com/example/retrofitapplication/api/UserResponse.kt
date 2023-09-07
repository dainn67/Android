package com.example.retrofitapplication.api

import com.example.retrofitapplication.model.User


data class UserResponse(
    val results: List<User>,
)