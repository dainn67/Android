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
import com.example.foodbookingapp.MainActivity.Companion.listDessert
import com.example.foodbookingapp.adapter.DessertAdapter

class DessertFrag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dessert, container, false)

        val dessertAdapter = DessertAdapter(rootView.context, R.layout.item_dessert, listDessert)
        rootView.findViewById<ListView>(R.id.lvDessert).adapter = dessertAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnDessertNext).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_dessertFrag_to_resultFrag)
        }

        view.findViewById<Button>(R.id.btnDessertBack).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_dessertFrag_to_mainDishFrag)
        }
    }
}