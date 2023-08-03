package com.example.workmanagingapp.view

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work

class MyViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private val tvTitle: TextView
    private val tvTime: TextView
    private val cbCheckDone: CheckBox

    init {
        Log.i(TAG, "init of MyViewHolder")
        tvTitle = itemView.findViewById(R.id.tvItemName)
        tvTime = itemView.findViewById(R.id.tvItemTime)
        cbCheckDone = itemView.findViewById(R.id.cbCheckDone)
    }

    fun bind(work: Work){
        tvTitle.text = work.getTitle()
        tvTime.text = work.getTime().toString()
        cbCheckDone.isChecked = work.getStatus()
    }
}