package com.example.foreground_service

import java.io.Serializable

class Song : Serializable {
    private var title: String
    private var singer: String
    private var res: Int

    constructor(title: String, singer: String, res: Int) {
        this.title = title
        this.singer = singer
        this.res = res
    }

    fun getTitle(): String {return title}
    fun getSinger(): String {return singer}
    fun getImgRes(): Int {return res}
}