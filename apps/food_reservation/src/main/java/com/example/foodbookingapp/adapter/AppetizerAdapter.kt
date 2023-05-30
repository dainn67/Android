package com.example.foodbookingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.foodbookingapp.R

class Appetizer(private val img: Int, private val name: String, private val desc: String, private val price: Double){
    fun getImg() = img
    fun getName() = name
    fun getDesc() = desc
    fun  getPrice() = price
}

class AppetizerAdapter(private val context: Context, private val id: Int, private val list: List<Appetizer>): ArrayAdapter<Appetizer>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        val appetizer: Appetizer = list[position]
        val img = appetizer.getImg()
        val name = appetizer.getName()
        val desc = appetizer.getDesc()
        val price = appetizer.getPrice()

        view.findViewById<ImageView>(R.id.appetizerImg).setImageResource(img)
        view.findViewById<TextView>(R.id.appetizerName).text = name
        view.findViewById<TextView>(R.id.appetizerDesc).text = desc
        view.findViewById<TextView>(R.id.appetizerPrice).text = "$${price}"

        return view
    }
}