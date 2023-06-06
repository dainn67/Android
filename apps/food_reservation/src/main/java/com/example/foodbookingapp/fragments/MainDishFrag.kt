package com.example.foodbookingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.example.foodbookingapp.R
import com.example.foodbookingapp.adapter.MainDishAdapter
import com.example.foodbookingapp.viewModel.MainDishViewModel

class MainDishFrag : Fragment() {

    val myMainDishVM = MainDishViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_dish, container, false)

        //add dishes to list
        myMainDishVM.addDishesToList()

        //use custom adapter to display the list
        val mainDishAdapter = MainDishAdapter(rootView.context, R.layout.item_maindish, myMainDishVM.getList())
        rootView.findViewById<ListView>(R.id.lvMainDish).adapter = mainDishAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //button to move to dessert
        view.findViewById<Button>(R.id.btnMainDishNext).setOnClickListener{
            myMainDishVM.moveToDessert(view)
        }

        //button to move back to appetizer
        view.findViewById<Button>(R.id.btnMainDishBack).setOnClickListener{
            myMainDishVM.moveToAppetizer(view)
        }

    }
}