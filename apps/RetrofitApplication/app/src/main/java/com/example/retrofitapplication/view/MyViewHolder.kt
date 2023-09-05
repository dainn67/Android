package com.example.retrofitapplication.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapplication.R

class MyViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val ivGender: ImageView
    private val tvName: TextView
    private val tvLocation: TextView

    init {
        ivGender = itemView.findViewById(R.id.itemIV)
        tvName = itemView.findViewById(R.id.itemTVName)
        tvLocation = itemView.findViewById(R.id.itemTVLocation)
    }

    fun bind() {

    }
}