package com.example.workmanagingapp.view.days

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R

class MyDayViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView){
    private val tvDayInWeek: TextView
    private val tvDate: TextView

    init {
        tvDayInWeek = itemView.findViewById(R.id.tvDayInWeek)
        tvDate = itemView.findViewById(R.id.tvDate)
    }

    fun bind(day: String){
        tvDayInWeek.text = day

        //TODO: get the corresponding date and bind to tvDate
    }
}