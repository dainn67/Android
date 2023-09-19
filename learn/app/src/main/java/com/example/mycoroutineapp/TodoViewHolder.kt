package com.example.mycoroutineapp

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvTitle: TextView
    private val tvUID: TextView
    private val tvID: TextView
    private val cbCompleted: CheckBox

    init {
        tvTitle = itemView.findViewById(R.id.tvTitle)
        tvUID = itemView.findViewById(R.id.tvUID)
        tvID = itemView.findViewById(R.id.tvID)
        cbCompleted = itemView.findViewById(R.id.cbCompleted)
    }

    fun bind(todo: Todo){
        tvTitle.text = todo.title
        tvUID.text = todo.userId.toString()
        tvID.text = todo.id.toString()
        cbCompleted.isChecked = todo.completed
    }
}