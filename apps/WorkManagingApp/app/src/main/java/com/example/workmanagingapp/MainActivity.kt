package com.example.workmanagingapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.view.addscreen.AddScreen
import com.example.workmanagingapp.view.mainscreen.days.MyDayAdapter
import com.example.workmanagingapp.view.mainscreen.todayWorks.MyTodayAdapter
import com.example.workmanagingapp.view.mainscreen.upcomingWorks.MyUpcomingAdapter
import com.example.workmanagingapp.viewmodel.MyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvTodayWork: TextView
    private lateinit var btnAdd: FloatingActionButton

    private lateinit var recyclerViewDays: RecyclerView
    private lateinit var recyclerViewCurrent: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var myViewModel: MyViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = MyViewModel()
        myViewModel.addSampleWorks()

        //TODO: load data from SQLite here

        tvTitle = findViewById(R.id.tvTitle)
        tvTitle.setOnClickListener{
            myViewModel.addSampleWorks()
        }

        tvTodayWork = findViewById(R.id.tvCurrent)
        tvTodayWork.text = "TODAY'S WORK - ${LocalDate.now().dayOfMonth}/${LocalDate.now().month.value}"

        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener{
            val intent = Intent(this, AddScreen::class.java)
            startActivity(intent)
        }

        recyclerViewDays = findViewById(R.id.recViewDays)
        recyclerViewDays.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDays.adapter = MyDayAdapter(this, myViewModel.getDayList())

        recyclerViewCurrent = findViewById(R.id.recViewCurrent)
        recyclerViewCurrent.layoutManager = LinearLayoutManager(this)
        recyclerViewCurrent.adapter = MyTodayAdapter(this, myViewModel.getWorkList())

        recyclerViewUpcoming = findViewById(R.id.recViewUpcoming)
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(this)
        recyclerViewUpcoming.adapter = MyUpcomingAdapter(this, myViewModel.getWorkList())

        observeList()
    }

    private fun observeList(){
        val workListLiveData = myViewModel.getWorkListLiveData()

        val observer = Observer<MutableList<Work>>{newList ->
            //update the list
            myViewModel.setWorkList(newList)

            //reset the adapter
            recyclerViewCurrent.adapter = MyTodayAdapter(this, myViewModel.getWorkList())
        }

        //observer will observe the list that is inside the value of its Livedata
        workListLiveData.observe(this, observer)
    }

}