package com.example.foodbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.foodbookingapp.MainActivity.Companion.listMainDish
import com.example.foodbookingapp.adapter.MainDishAdapter

class MainDishFrag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_dish, container, false)

        val mainDishAdapter = MainDishAdapter(rootView.context, R.layout.item_maindish, listMainDish)
        rootView.findViewById<ListView>(R.id.lvMainDish).adapter = mainDishAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //button to move to dessert
        view.findViewById<Button>(R.id.btnMainDishNext).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainDishFrag_to_dessertFrag)
        }

        //button to move back to appetizer
        view.findViewById<Button>(R.id.btnMainDishBack).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainDishFrag_to_appetizerFrag)
        }

    }
}