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
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.foodbookingapp.FragmentType
import com.example.foodbookingapp.MainActivity
import com.example.foodbookingapp.MainActivity.Companion.INITIAL_AMOUNT
import com.example.foodbookingapp.MainActivity.Companion.TAG
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Dish
import com.example.foodbookingapp.viewModel.FragmentsViewModel
import java.security.acl.Owner

class DishAdapter(
    private val fragmentType: FragmentType,
    private var owner: LifecycleOwner,
    private val myViewModel: FragmentsViewModel,
    private val context: Context,
    private val id: Int,
    private val list: List<Dish>
) : ArrayAdapter<Dish>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var amount = INITIAL_AMOUNT

        //inflate the item's view
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: myInflater.inflate(id, null, false)

        //minus, amount and plus button
        val imgMinus = view.findViewById<ImageView>(R.id.itemMinusImg)
        val tvAmount = view.findViewById<TextView>(R.id.itemAmount)
        val imgPlus = view.findViewById<ImageView>(R.id.itemPlusImg)

        //get the dish from the list
        val dish = list[position]

        //view binding
        view.findViewById<ImageView>(R.id.itemImg).setImageResource(dish.getImg())
        view.findViewById<TextView>(R.id.itemName).text = dish.getName()
        val tvDesc = view.findViewById<TextView>(R.id.itemDesc)
        tvDesc.text = dish.getDesc()
        tvDesc.isSelected = true
        view.findViewById<TextView>(R.id.itemPrice).text = "$${dish.getPrice()}"

        //get the current fragment's map (livedata)
        val myLiveDataMap = when (fragmentType) {
            FragmentType.APPETIZER -> myViewModel.getLiveDataMapAppetizer()
            FragmentType.MAIN_DISH -> myViewModel.getLiveDataMapMainDish()
            FragmentType.DESSERT -> myViewModel.getLiveDataMapDessert()
        }

        var myMap = myLiveDataMap.value

        val llAmount = view.findViewById<LinearLayout>(R.id.itemLLAmount)
        val checkBoxOrder = view.findViewById<CheckBox>(R.id.itemCheckBox)

        //observer
        val myObserver = Observer<MutableMap<Int, Int>> { newMap ->
            myMap = newMap
            if (myMap!![position] != 0) {
                checkBoxOrder.isChecked = true
                llAmount.visibility = View.VISIBLE
                tvAmount.text = myMap!![position].toString()
            }else{
                checkBoxOrder.isChecked = false
                llAmount.visibility = View.GONE
            }
        }

        //observe changes
        myLiveDataMap.observe(owner, myObserver)

        //set checkbox's behaviour
        checkBoxOrder.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                myMap!![position] = 1
                myLiveDataMap.value = myMap     //notify changed
            } else {
                myMap!![position] = 0
                myLiveDataMap.value = myMap     //notify changed
            }
        }

        imgMinus.setOnClickListener {
            if (amount > 1) amount--
            myMap!![position] = amount
            myLiveDataMap.value = myMap     //notify changed
        }
        imgPlus.setOnClickListener {
            if (amount < 10) amount++
            myMap!![position] = amount
            myLiveDataMap.value = myMap     //notify changed
        }

        return view
    }
}