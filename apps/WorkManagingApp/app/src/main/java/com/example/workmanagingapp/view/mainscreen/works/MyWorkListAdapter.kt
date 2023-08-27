package com.example.workmanagingapp.view.mainscreen.works

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.viewmodel.MyViewModel
import com.example.workmanagingapp.viewmodel.OnItemClickListener

@RequiresApi(Build.VERSION_CODES.O)
class MyWorkListAdapter(
    private val listener: OnItemClickListener,
    private val context: Context,
    private val viewModel: MyViewModel,
    private val type: Constants.Companion.ViewDetailType
) : RecyclerView.Adapter<MyWorkListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWorkListViewHolder {
        //return the custom viewHolder with the layout view
        return MyWorkListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_layout_work, parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: MyWorkListViewHolder, position: Int) {
        when (type) {
            Constants.Companion.ViewDetailType.CURRENT -> {
                Log.i(TAG, "CURRENT")
                holder.bindCurrentLayout(viewModel.getCurrentWorkList()[position])
            }
            Constants.Companion.ViewDetailType.UPCOMING -> {
                Log.i(TAG, "UPCOMING")
                holder.bindUpcomingLayout(viewModel.getUpcomingWorkList()[position])
            }
            Constants.Companion.ViewDetailType.ALL -> {
                Log.i(TAG, "ALL")
                holder.bindUpcomingLayout(viewModel.getAllWorkList()[position])
            }
        }

        holder.itemView.setOnClickListener {
            listener.onItemWorkClick(position, type)
        }

        holder.itemView.setOnLongClickListener {
            listener.onItemWorkLongClick(position, type)
            true
        }
    }

    override fun getItemCount(): Int {
        return when(type){
            Constants.Companion.ViewDetailType.CURRENT -> viewModel.getCurrentWorkList().size
            Constants.Companion.ViewDetailType.UPCOMING -> viewModel.getUpcomingWorkList().size
            Constants.Companion.ViewDetailType.ALL -> viewModel.getAllWorkList().size
        }
    }
}