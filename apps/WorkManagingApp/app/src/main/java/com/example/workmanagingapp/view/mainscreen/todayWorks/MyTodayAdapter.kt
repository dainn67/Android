package com.example.workmanagingapp.view.mainscreen.todayWorks

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.view.mainscreen.OnItemClickListener

class MyTodayAdapter(
    private val listener: OnItemClickListener,
    private val context: Context,
    private val list: MutableList<Work>
) : RecyclerView.Adapter<MyTodayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodayViewHolder {
        //return the custom viewHolder with the layout view
        val itemView = LayoutInflater.from(context).inflate(R.layout.work_item_layout, parent, false)
        return MyTodayViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyTodayViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{
            listener.onItemTodayClick(position)
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
