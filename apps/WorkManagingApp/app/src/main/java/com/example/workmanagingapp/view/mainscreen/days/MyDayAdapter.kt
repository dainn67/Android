package com.example.workmanagingapp.view.mainscreen.days

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Day
import com.example.workmanagingapp.view.mainscreen.OnItemClickListener

class MyDayAdapter(
    private val listener: OnItemClickListener,
    private val context: Context,
    private val list: MutableList<Day>
): RecyclerView.Adapter<MyDayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDayViewHolder {
        return MyDayViewHolder(LayoutInflater.from(context).inflate(R.layout.day_item_layout, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyDayViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener{
            listener.onItemDayClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}