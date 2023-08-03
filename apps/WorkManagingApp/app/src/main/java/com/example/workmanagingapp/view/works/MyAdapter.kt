package com.example.workmanagingapp.view.works

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work

class MyAdapter(
    private val context: Context,
    private val list: MutableList<Work>
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        Log.i(TAG, "onCreateViewHolder of MyAdapter")

        //return the custom viewHolder with the layout view
        val itemView = LayoutInflater.from(context).inflate(R.layout.work_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder of MyAdapter")
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        Log.i(TAG, "getItemCount of MyAdapter: ${list.size}")
        return list.size
    }
}
