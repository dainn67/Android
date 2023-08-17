package com.example.workmanagingapp.view.mainscreen.works

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.viewmodel.OnItemClickListener

@RequiresApi(Build.VERSION_CODES.O)
class MyWorkListAdapter(
    private val listener: OnItemClickListener,
    private val context: Context,
    private val list: MutableList<Work>,
    private val type: Constants.Companion.ViewDetailType
) : RecyclerView.Adapter<MyWorkListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWorkListViewHolder {
        //return the custom viewHolder with the layout view
        return MyWorkListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_layout_work, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyWorkListViewHolder, position: Int) {
        if (type == Constants.Companion.ViewDetailType.TODAY)
            holder.bindTodayLayout(list[position])
        else
            holder.bindUpcomingLayout(list[position])

        holder.itemView.setOnClickListener {
            listener.onItemWorkClick(position)
        }
        holder.itemView.setOnLongClickListener {
            listener.onItemWorkLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}