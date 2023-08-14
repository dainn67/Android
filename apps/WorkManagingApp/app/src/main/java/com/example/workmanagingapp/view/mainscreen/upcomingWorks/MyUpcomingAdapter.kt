package com.example.workmanagingapp.view.mainscreen.upcomingWorks

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.view.mainscreen.OnItemClickListener

class MyUpcomingAdapter(
    private val listener: OnItemClickListener,
    private val context: Context,
    private val list: MutableList<Work>
): RecyclerView.Adapter<MyUpcomingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUpcomingViewHolder {
        return MyUpcomingViewHolder(LayoutInflater.from(context).inflate(R.layout.work_item_layout, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyUpcomingViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{
            listener.onItemUpcomingClick(position)
        }
        holder.itemView.setOnLongClickListener {
            listener.onItemLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}