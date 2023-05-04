package com.example.foreground_service

import java.io.Serializable

class Song : Serializable {
    private var title: String
    private var singer: String
    private var musicRes: Int
    private var imgRes: Int

    constructor(title: String, singer: String, res: Int, imgRes: Int) {
        this.title = title
        this.singer = singer
        this.musicRes = res
        this.imgRes = imgRes
    }

    fun getTitle(): String {return title}
    fun getSinger(): String {return singer}
    fun getMusicRes(): Int {return musicRes}
    fun getImgRes(): Int {return imgRes}
}