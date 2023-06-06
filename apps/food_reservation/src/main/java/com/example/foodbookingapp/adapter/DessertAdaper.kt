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
import com.example.foodbookingapp.model.Dessert
import com.example.foodbookingapp.model.Dish



class DessertAdapter(
    private val context: Context,
    private val id: Int,
    private val list: List<Dessert>
) : ArrayAdapter<Dessert>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var amount = 1

        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        val dessert = list[position]

        view.findViewById<ImageView>(R.id.dessertImg).setImageResource(dessert.getImg())
        view.findViewById<TextView>(R.id.dessertName).text = dessert.getName()
        val tvDesc = view.findViewById<TextView>(R.id.dessertDesc)
        tvDesc.text = dessert.getDesc()
        tvDesc.isSelected = true
        view.findViewById<TextView>(R.id.dessertPrice).text = "$${dessert.getPrice()}"

        val checkBoxOrder = view.findViewById<CheckBox>(R.id.checkboxOrderDessert)
        val llAmount = view.findViewById<LinearLayout>(R.id.llAmountDessert)
        checkBoxOrder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) llAmount.visibility = View.VISIBLE
            else llAmount.visibility = View.GONE
        }

        val imgMinus = view.findViewById<ImageView>(R.id.imgMinusDessert)
        val tvAmount = view.findViewById<TextView>(R.id.tvAmountDessert)
        val imgPlus = view.findViewById<ImageView>(R.id.imgPlusDessert)
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
