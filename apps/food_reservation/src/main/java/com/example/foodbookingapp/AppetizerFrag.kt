package com.example.foodbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.foodbookingapp.MainActivity.Companion.listAppetizer
import com.example.foodbookingapp.adapter.AppetizerAdapter

class AppetizerFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_appetizer, container, false)

        val tvTitle = rootView.findViewById<TextView>(R.id.appetizerTitle)
        tvTitle.setOnClickListener{
            Navigation.findNavController(rootView).navigate(R.id.action_appetizerFrag_to_mainDishFrag)
        }

        val appetizerAdapter = AppetizerAdapter(rootView.context, R.layout.item_appetizer, listAppetizer)
        rootView.findViewById<ListView>(R.id.lvAppetizer).adapter = appetizerAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAppetizerNext).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_appetizerFrag_to_mainDishFrag)
        }
    }
}