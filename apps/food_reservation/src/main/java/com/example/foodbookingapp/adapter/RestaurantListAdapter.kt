package com.example.foodbookingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.foodbookingapp.R

class Restaurant(private val img: Int, private val name: String, private val address: String, private val rating: Int){
    fun getImg() = img
    fun getName() = name
    fun getAddress() = address
    fun getRating() = rating
}

class RestaurantListAdapter(private val context: Context, private val id: Int, private val list: List<Restaurant>): ArrayAdapter<Restaurant>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        val restaurant: Restaurant = list[position]
        val img = restaurant.getImg()
        val name = restaurant.getName()
        val address = restaurant.getAddress()
        val rating = restaurant.getRating()

        view.findViewById<ImageView>(R.id.restaurantImg).setImageResource(img)
        view.findViewById<TextView>(R.id.restaurantName).text = name
        view.findViewById<TextView>(R.id.restaurantAddress).text = address
        view.findViewById<TextView>(R.id.restaurantRating).text = "Rating: $rating/5"

        return view
    }
}