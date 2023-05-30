package com.example.foodbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation

class resultFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_result, container, false)

        val tvTitle = rootView.findViewById<TextView>(R.id.resultTitle)
        tvTitle.setOnClickListener{
            Navigation.findNavController(rootView).navigate(R.id.action_resultFrag_to_restaurantFrag)
        }

        return rootView
    }

}