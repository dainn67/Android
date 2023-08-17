package com.example.workmanagingapp.viewmodel

interface OnItemClickListener {
    fun onItemDayClick(position: Int)
    fun onItemWorkClick(position: Int)
    fun onItemWorkLongClick(position: Int)
}