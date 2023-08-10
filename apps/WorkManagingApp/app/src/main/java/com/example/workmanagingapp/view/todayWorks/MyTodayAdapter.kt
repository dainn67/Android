package com.example.workmanagingapp.view.todayWorks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work

class MyTodayAdapter(
    private val context: Context,
    private val list: MutableList<Work>
) : RecyclerView.Adapter<MyTodayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodayViewHolder {
        //return the custom viewHolder with the layout view
        val itemView = LayoutInflater.from(context).inflate(R.layout.work_item_layout, parent, false)
        return MyTodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyTodayViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
