package com.example.foodbookingapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.foodbookingapp.FragmentType
import com.example.foodbookingapp.MainActivity.Companion.TAG
import com.example.foodbookingapp.MainActivity.Companion.myViewModel
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.DishesList
import com.example.foodbookingapp.view.adapter.DishAdapter

class AppetizerFrag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_appetizer, container, false)

        //create a custom adapter to display the list
        val appetizerAdapter = DishAdapter(
            FragmentType.APPETIZER,
            this,
            myViewModel,
            rootView.context,
            R.layout.item_dish,
            myViewModel.getAppetizerList()
        )
        rootView.findViewById<ListView>(R.id.lvAppetizer).adapter = appetizerAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //move to next fragment
        view.findViewById<Button>(R.id.btnAppetizerNext).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_appetizerFrag_to_mainDishFrag)
        }
    }
}