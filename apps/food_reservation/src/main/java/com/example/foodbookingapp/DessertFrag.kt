package com.example.foodbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation

class DessertFrag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dessert, container, false)

        val tvTitle = rootView.findViewById<TextView>(R.id.dessertTitle)
        tvTitle.setOnClickListener{
            Navigation.findNavController(rootView).navigate(R.id.action_dessertFrag_to_resultFrag)
        }

        return rootView
    }
}