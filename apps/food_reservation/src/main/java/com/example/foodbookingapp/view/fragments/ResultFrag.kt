package com.example.foodbookingapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.foodbookingapp.MainActivity.Companion.TAG
import com.example.foodbookingapp.R
import com.example.foodbookingapp.view.adapter.ResultAdapter
import com.example.foodbookingapp.model.ResultDish
import com.example.foodbookingapp.viewModel.ResultViewModel

class ResultFrag : Fragment() {
    private val resultVM = ResultViewModel()
    //    private val resultVM = ViewModelProvider(this)[ResultViewModel::class.java]

    private lateinit var lvResult: ListView
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_result, container, false)

        resultAdapter = ResultAdapter(rootView.context, R.layout.item_result, resultVM.getListResult())
        lvResult = rootView.findViewById(R.id.lvRes)
        lvResult.adapter = resultAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultVM.addToResult()

        //observer
        val myObserverList = Observer<List<ResultDish>> { newData ->
            resultAdapter = ResultAdapter(view.context, R.layout.item_result, newData)
            lvResult.adapter = resultAdapter
        }
        val myObserverCost = Observer<Double> { newData ->
            view.findViewById<TextView>(R.id.tvTotal).text = "Total: $${newData}"
        }
        resultVM.getLiveData().observe(viewLifecycleOwner, myObserverList)
        resultVM.getLiveDataTotalCost().observe(viewLifecycleOwner, myObserverCost)

        //button to move back to order
        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_resultFrag_to_dessertFrag)
        }

        //button order
        view.findViewById<Button>(R.id.btnOrder).setOnClickListener {
//            myMainDishVM.moveToDessert(view)
            Toast.makeText(view.context, "Order created", Toast.LENGTH_SHORT).show()
        }
    }
}