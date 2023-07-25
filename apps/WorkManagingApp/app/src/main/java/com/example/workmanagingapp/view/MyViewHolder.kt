package com.example.workmanagingapp.view

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvTitle: TextView
    private val tvTime: TextView
    private val cbCheckDone: CheckBox

    init {
        tvTitle = itemView.findViewById(R.id.tvTitle)
        tvTime = itemView.findViewById(R.id.tvTime)
        cbCheckDone = itemView.findViewById(R.id.cbCheckDone)
    }
}