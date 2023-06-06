package com.example.foodbookingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Appetizer
import com.example.foodbookingapp.model.Dish



class AppetizerAdapter(
    private val context: Context,
    private val id: Int,
    private val list: List<Appetizer>
) : ArrayAdapter<Appetizer>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var amount = 1

        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        val appetizer: Appetizer = list[position]


        view.findViewById<ImageView>(R.id.appetizerImg).setImageResource(appetizer.getImg())
        view.findViewById<TextView>(R.id.appetizerName).text = appetizer.getName()

        val tvDesc = view.findViewById<TextView>(R.id.appetizerDesc)
        tvDesc.text = appetizer.getDesc()
        tvDesc.isSelected = true

        view.findViewById<TextView>(R.id.appetizerPrice).text = "$${appetizer.getPrice()}"


        val checkBoxOrder = view.findViewById<CheckBox>(R.id.checkboxOrderAppetizer)
        val llAmount = view.findViewById<LinearLayout>(R.id.llAmountAppetizer)
        checkBoxOrder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) llAmount.visibility = View.VISIBLE
            else llAmount.visibility = View.GONE
        }

        val imgMinus = view.findViewById<ImageView>(R.id.imgMinusAppetizer)
        val tvAmount = view.findViewById<TextView>(R.id.tvAmountAppetizer)
        val imgPlus = view.findViewById<ImageView>(R.id.imgPlusAppetizer)
        imgMinus.setOnClickListener {
            if (amount > 0) amount--
            tvAmount.text = amount.toString()
        }
        imgPlus.setOnClickListener {
            if (amount < 10) amount++
            tvAmount.text = amount.toString()
        }

        return view
    }
}