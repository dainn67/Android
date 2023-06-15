package com.example.backgroundservice

import java.io.Serializable

class Song (private val name: String, private val res: Int): Serializable{
    fun getName() = name
    fun getRes() = res
}