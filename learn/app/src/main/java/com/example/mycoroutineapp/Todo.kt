package com.example.mycoroutineapp

data class TodoResponse(
    val list: List<Todo>
)

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)