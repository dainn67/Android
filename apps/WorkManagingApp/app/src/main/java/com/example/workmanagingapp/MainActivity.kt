package com.example.workmanagingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.view.MyAdapter
import com.example.workmanagingapp.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout.TabGravity

class MainActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = MyViewModel()
        myViewModel.addSampleWorks()
        //TODO: load data from SQLite here

        tvTitle = findViewById(R.id.tvTitle)
        tvTitle.setOnClickListener{
            Log.i(TAG, "add...")
            myViewModel.addSampleWorks()
        }

        recyclerView = findViewById(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(this, myViewModel.getWorkList())

        observeList()
    }

    private fun observeList(){
        val workListLiveData = myViewModel.getWorkListLiveData()

        val observer = Observer<MutableList<Work>>{newList ->
            myViewModel.setWorkList(newList)

            //reset the adapter
            recyclerView.adapter = MyAdapter(this, myViewModel.getWorkList())
        }

        workListLiveData.observe(this, observer)
    }
}