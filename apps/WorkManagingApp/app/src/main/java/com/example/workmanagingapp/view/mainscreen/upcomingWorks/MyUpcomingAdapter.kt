package com.example.workmanagingapp.view.mainscreen.upcomingWorks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work

class MyUpcomingAdapter(
    private val context: Context,
    private val list: MutableList<Work>
): RecyclerView.Adapter<MyUpcomingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUpcomingViewHolder {
        return MyUpcomingViewHolder(LayoutInflater.from(context).inflate(R.layout.work_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyUpcomingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}