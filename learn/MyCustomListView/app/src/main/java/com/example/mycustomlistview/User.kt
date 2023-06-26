package com.example.mycustomlistview

enum class Gender{
    MALE, FEMALE
}

class User(private val name: String, private val age: Int, private val gender: Gender) {
    fun getName() = name
    fun getAge() = age
    fun getGender() = gender
}