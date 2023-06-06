package com.example.foodbookingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.foodbookingapp.R
import com.example.foodbookingapp.adapter.AppetizerAdapter
import com.example.foodbookingapp.viewModel.AppetizerViewModel

class AppetizerFrag : Fragment() {

    private val myAppetizerVM = AppetizerViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_appetizer, container, false)

        //add dishes to list
        myAppetizerVM.addDishesToList()

        //use custom adapter to dsplay the list
        val appetizerAdapter = AppetizerAdapter(
            rootView.context,
            R.layout.item_appetizer, myAppetizerVM.getList()
        )
        rootView.findViewById<ListView>(R.id.lvAppetizer).adapter = appetizerAdapter


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAppetizerNext).setOnClickListener {

            //move to next fragment
            myAppetizerVM.moveToMainDish(view)
        }
    }
}