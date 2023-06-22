package com.example.myforegroundservice

import java.io.Serializable

class Song (private val img:Int, private val title: String, private val singer: String, private val res: Int): Serializable {
    fun getImg() = img
    fun getTitle() = title
    fun getSinger() = singer
    fun getRes() = res
}