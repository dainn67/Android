package com.example.workmanagingapp.view.mainscreen.todayWorks

import android.os.Build
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.viewmodel.MyViewModel.Companion.displayTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(work: Work){
        tvTitle.text = work.getTitle()
        tvTime.text = displayTime(work.getTime())
        cbCheckDone.isChecked = work.getStatus()
    }
}