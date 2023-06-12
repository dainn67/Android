package com.example.foodbookingapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.navigation.Navigation
import com.example.foodbookingapp.FragmentType
import com.example.foodbookingapp.MainActivity.Companion.myViewModel
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.DishesList
import com.example.foodbookingapp.view.adapter.DishAdapter

class DessertFrag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dessert, container, false)

        //use custom adapter to display the list
        val dessertAdapter = DishAdapter(
            FragmentType.DESSERT,
            this,
            myViewModel,
            rootView.context,
            R.layout.item_dish,
            myViewModel.getDessertList()
        )
        rootView.findViewById<ListView>(R.id.lvDessert).adapter = dessertAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnDessertNext).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_dessertFrag_to_resultFrag)
        }

        view.findViewById<Button>(R.id.btnDessertBack).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_dessertFrag_to_mainDishFrag)
        }
    }
}