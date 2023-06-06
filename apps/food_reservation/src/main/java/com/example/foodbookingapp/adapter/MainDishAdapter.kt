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
import com.example.foodbookingapp.model.MainDish


class MainDishAdapter(private val context: Context, private val id: Int, private val list: List<MainDish>): ArrayAdapter<MainDish>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var amount = 1

        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        val dish = list[position]

        view.findViewById<ImageView>(R.id.mainDishImg).setImageResource(dish.getImg())
        view.findViewById<TextView>(R.id.mainDishName).text = dish.getName()
        val tvDesc = view.findViewById<TextView>(R.id.mainDishDesc)
        tvDesc.text = dish.getDesc()
        tvDesc.isSelected = true
        view.findViewById<TextView>(R.id.mainDishPrice).text = "$${dish.getPrice()}"

        val checkBoxOrder = view.findViewById<CheckBox>(R.id.checkboxOrderMain)
        val llAmount = view.findViewById<LinearLayout>(R.id.llAmountMain)
        checkBoxOrder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) llAmount.visibility = View.VISIBLE
            else llAmount.visibility = View.GONE
        }

        val imgMinus = view.findViewById<ImageView>(R.id.imgMinusMain)
        val tvAmount = view.findViewById<TextView>(R.id.tvAmountMain)
        val imgPlus = view.findViewById<ImageView>(R.id.imgPlusMain)
        imgMinus.setOnClickListener{
            if(amount > 0) amount--
            tvAmount.text = amount.toString()
        }
        imgPlus.setOnClickListener{
            if(amount < 10) amount++
            tvAmount.text = amount.toString()
        }

        return view
    }
}
