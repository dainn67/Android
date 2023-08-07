package com.example.workmanagingapp.view.days

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Day

class MyDayAdapter(
    private val context: Context,
    private val list: MutableList<Day>
): RecyclerView.Adapter<MyDayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDayViewHolder {
        return MyDayViewHolder(LayoutInflater.from(context).inflate(R.layout.day_layout, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyDayViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}