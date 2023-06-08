package com.example.foodbookingapp.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.foodbookingapp.FragmentType
import com.example.foodbookingapp.MainActivity
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Dish
import com.example.foodbookingapp.viewModel.FragmentsViewModel

class DishAdapter(
    private val fragmentType: FragmentType,
    private val myViewModel: FragmentsViewModel,
    private val context: Context,
    private val id: Int,
    private val list: List<Dish>
): ArrayAdapter<Dish>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var amount = 1

        //inflate the item's view
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        //get the dish from the list and bind view
        val dish = list[position]

        view.findViewById<ImageView>(R.id.itemImg).setImageResource(dish.getImg())
        view.findViewById<TextView>(R.id.itemName).text = dish.getName()
        val tvDesc = view.findViewById<TextView>(R.id.itemDesc)
        tvDesc.text = dish.getDesc()
        tvDesc.isSelected = true
        view.findViewById<TextView>(R.id.itemPrice).text = "$${dish.getPrice()}"

        //get the current fragment's map
        val myMap = when(fragmentType){
            FragmentType.APPETIZER -> myViewModel.getAppetizerOrderMap()
            FragmentType.MAIN_DISH -> myViewModel.getMainDishOrderMap()
            FragmentType.DESSERT -> myViewModel.getDessertOrderMap()
        }

        //set checkbox's behaviour
        val checkBoxOrder = view.findViewById<CheckBox>(R.id.itemCheckBox)
        val llAmount = view.findViewById<LinearLayout>(R.id.itemLLAmount)
        checkBoxOrder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                llAmount.visibility = View.VISIBLE
                myMap[position] = 1
            }
            else {
                llAmount.visibility = View.GONE
                myMap[position] = 0
            }
        }

        val imgMinus = view.findViewById<ImageView>(R.id.itemMinusImg)
        val tvAmount = view.findViewById<TextView>(R.id.itemAmount)
        val imgPlus = view.findViewById<ImageView>(R.id.itemPlusImg)
        imgMinus.setOnClickListener {
            if (amount > 1) amount--
            tvAmount.text = amount.toString()
            myMap[position] = amount
            debugPrtMap(myMap)
        }
        imgPlus.setOnClickListener {
            if (amount < 10) amount++
            tvAmount.text = amount.toString()
            myMap[position] = amount
            debugPrtMap(myMap)
        }

        return view
    }

    private fun debugPrtMap(myMap: Map<Int, Int>){
        Log.i(MainActivity.TAG, "---${fragmentType}-----------")
        for(id in list.indices){
            Log.i(MainActivity.TAG, myMap[id].toString())
        }
        Log.i(MainActivity.TAG, "--------------")
    }
}