package com.example.retrofitapplication.model

import java.io.Serializable
import java.util.UUID

class Login(
    private val uuid: UUID,
    private val username: String,
    private val password: String,
    private val salt: String,
    private val md5: String,
    private val sha1: String,
    private val sha256: String,
):Serializable {
}