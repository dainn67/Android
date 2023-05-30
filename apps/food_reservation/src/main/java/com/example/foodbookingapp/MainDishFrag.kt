package com.example.foodbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation

class MainDishFrag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_dish, container, false)

        val tvTitle = rootView.findViewById<TextView>(R.id.mainDishTitle)
        tvTitle.setOnClickListener{
            Navigation.findNavController(rootView).navigate(R.id.action_mainDishFrag_to_dessertFrag)
        }

        return rootView
    }
}