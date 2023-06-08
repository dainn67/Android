package com.example.foodbookingapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.foodbookingapp.MainActivity.Companion.TAG
import com.example.foodbookingapp.R
import com.example.foodbookingapp.view.adapter.ResultAdapter
import com.example.foodbookingapp.model.Dish
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
        resultVM.initData()

        resultAdapter = ResultAdapter(rootView.context, R.layout.item_result, resultVM.getListResult())
        lvResult = rootView.findViewById(R.id.lvRes)
        lvResult.adapter = resultAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //button to move back to order
        view.findViewById<Button>(R.id.btnBack).setOnClickListener {

            resultVM.addDummyData()

            val myObserver = Observer<List<Dish>> { newData ->
                resultAdapter = ResultAdapter(view.context, R.layout.item_result, newData)
                lvResult.adapter = resultAdapter
            }

            Log.i(TAG, "HERE")

            resultVM.getLiveData().observe(viewLifecycleOwner, myObserver)
        }

        //button order
        view.findViewById<Button>(R.id.btnOrder).setOnClickListener {
//            myMainDishVM.moveToDessert(view)
            Toast.makeText(view.context, "Hi", Toast.LENGTH_SHORT).show()
        }
    }
}