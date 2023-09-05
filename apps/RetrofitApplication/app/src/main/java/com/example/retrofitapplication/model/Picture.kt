package com.example.retrofitapplication.model

import java.io.Serializable

class Picture(
    private val large: String,
    private val medium: String,
    private val thumbnail: String,
    ) : Serializable {
}