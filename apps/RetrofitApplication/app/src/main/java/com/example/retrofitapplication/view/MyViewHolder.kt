package com.example.retrofitapplication.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapplication.R
import com.example.retrofitapplication.model.User

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

    fun bind(user: User) {
        ivGender.setImageResource(if(user.gender == "female") R.drawable.female else R.drawable.male)
        with(user.name){
            tvName.text = "$title. $first $last"
        }
        with(user.location){
            tvLocation.text = "${street.number} ${street.name}, $city, $state, $country"
        }
    }
}