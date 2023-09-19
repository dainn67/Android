package com.example.mycoroutineapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTODO {

    @GET("todos")
    fun getTODOList(): Call<List<Todo>>
}