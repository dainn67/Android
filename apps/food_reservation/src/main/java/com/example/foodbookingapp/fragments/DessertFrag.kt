package com.example.foodbookingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.example.foodbookingapp.R
import com.example.foodbookingapp.adapter.DessertAdapter
import com.example.foodbookingapp.viewModel.DessertViewModel

class DessertFrag : Fragment() {

    private val myDessertVM = DessertViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dessert, container, false)

        //add dishes to the list
        myDessertVM.addDishesToList()

        //use custom adapter to display the list
        val dessertAdapter = DessertAdapter(rootView.context, R.layout.item_dessert, myDessertVM.getList())
        rootView.findViewById<ListView>(R.id.lvDessert).adapter = dessertAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnDessertNext).setOnClickListener{
            myDessertVM.moveToResult(view)
        }

        view.findViewById<Button>(R.id.btnDessertBack).setOnClickListener{
            myDessertVM.moveToMainDish(view)
        }
    }
}