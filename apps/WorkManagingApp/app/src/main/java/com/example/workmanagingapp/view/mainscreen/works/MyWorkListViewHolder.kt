package com.example.workmanagingapp.view.mainscreen.works

import android.os.Build
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.viewmodel.MyViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MyWorkListViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val tvTitle: TextView
    private val tvTime: TextView
    private val cbCheckDone: CheckBox

    init {
        tvTitle = itemView.findViewById(R.id.tvItemName)
        tvTime = itemView.findViewById(R.id.tvItemTime)
        cbCheckDone = itemView.findViewById(R.id.cbCheckDone)
    }


    fun bindTodayLayout(work: Work){
        tvTitle.text = work.getTitle()
        tvTime.text = MyViewModel.displayTime(work.getTime())
        cbCheckDone.isChecked = work.getStatus()
    }

    fun bindUpcomingLayout(work: Work){
        val displayTime = MyViewModel.displayTime(work.getTime())
        val day = work.getTime().date
        val month = work.getTime().month + 1    //0 based

        tvTitle.text = work.getTitle()
        tvTime.text = "$day/$month - $displayTime"
        cbCheckDone.isChecked = work.getStatus()
    }
}