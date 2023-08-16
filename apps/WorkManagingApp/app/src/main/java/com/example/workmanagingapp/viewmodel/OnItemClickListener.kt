package com.example.workmanagingapp.viewmodel

interface OnItemClickListener {
    fun onItemDayClick(position: Int)
    fun onItemTodayClick(position: Int)
    fun onItemUpcomingClick(position: Int)
    fun onItemLongClick(position: Int)
}