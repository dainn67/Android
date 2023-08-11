package com.example.workmanagingapp.view.mainscreen.todayWorks

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work

class MyTodayViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private val tvTitle: TextView
    private val tvTime: TextView
    private val cbCheckDone: CheckBox

    init {
        tvTitle = itemView.findViewById(R.id.tvItemName)
        tvTime = itemView.findViewById(R.id.tvItemTime)
        cbCheckDone = itemView.findViewById(R.id.cbCheckDone)
    }

    fun bind(work: Work){
        val displayHour = if(work.getTime().hours < 10) "0${work.getTime().hours}" else work.getTime().hours
        val displayMinute = if(work.getTime().minutes < 10) "0${work.getTime().minutes}" else work.getTime().minutes

        tvTitle.text = work.getTitle()
        tvTime.text = "$displayHour:$displayMinute"
        cbCheckDone.isChecked = work.getStatus()
    }
}