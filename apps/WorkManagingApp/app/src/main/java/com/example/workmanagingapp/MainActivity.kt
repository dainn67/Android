package com.example.workmanagingapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.model.Constants
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Day
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.view.addscreen.AddScreen
import com.example.workmanagingapp.view.mainscreen.works.DialogDelete
import com.example.workmanagingapp.view.mainscreen.works.DialogViewDetail
import com.example.workmanagingapp.view.mainscreen.days.MyDayAdapter
import com.example.workmanagingapp.viewmodel.OnItemClickListener
import com.example.workmanagingapp.view.mainscreen.works.MyWorkListAdapter
import com.example.workmanagingapp.viewmodel.MyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var tvTitle: TextView

    private lateinit var tvTodayWork: TextView

    private lateinit var btnAdd: FloatingActionButton
    private lateinit var dropdownToday: TextView
    private lateinit var dropdownUpcoming: TextView

    private lateinit var recyclerViewDays: RecyclerView
    private lateinit var recyclerViewCurrent: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = MyViewModel(this)

        //receive new work from add screen
        val work = intent.getSerializableExtra("new_work") as Work?
        work?.let {
            Log.i(TAG, "Main received: $work")
            myViewModel.addNewToList(it)
        }

        //Load data from SQLite using content provider
//        myViewModel.loadWorkList(LocalDate.now(), Constants.Companion.ViewDetailType.TODAY)
//        myViewModel.loadWorkList(LocalDate.now(), Constants.Companion.ViewDetailType.UPCOMING)
        myViewModel.loadWorkList(LocalDate.now(), Constants.Companion.ViewDetailType.ALL)

        //debug: Title: Work managing app
        tvTitle = findViewById(R.id.tvTitle)
        tvTitle.setOnClickListener {}

        //add new work button
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddScreen::class.java)
            startActivity(intent)
        }

        //today's work with current date
        tvTodayWork = findViewById(R.id.tvCurrent)
        tvTodayWork.text =
            "TODAY'S WORK - ${LocalDate.now().dayOfMonth}/${LocalDate.now().month.value}"

        //set recyclerViews
        setRecyclerViews()

        //dropdown buttons
        setDropDownButtons()

        observeDayList()
        observeCurrentTitle()
        observeWorkList()
    }

    private fun setRecyclerViews() {
        //days tab
        recyclerViewDays = findViewById(R.id.recViewDays)
        recyclerViewDays.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDays.adapter = MyDayAdapter(this, this, myViewModel)

        //current view
        recyclerViewCurrent = findViewById(R.id.recViewCurrent)
        recyclerViewCurrent.layoutManager = LinearLayoutManager(this)
        recyclerViewCurrent.adapter = MyWorkListAdapter(
            this,
            this,
            myViewModel.getCurrentWorkList(),
            Constants.Companion.ViewDetailType.TODAY
        )

        //upcoming view
        recyclerViewUpcoming = findViewById(R.id.recViewUpcoming)
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(this)
        recyclerViewUpcoming.adapter = MyWorkListAdapter(
            this,
            this,
            myViewModel.getUpcomingWorkList(),
            Constants.Companion.ViewDetailType.UPCOMING
        )
    }

    private fun setDropDownButtons() {
        dropdownToday = findViewById(R.id.dropDownToday)
        dropdownToday.setOnClickListener {
            if (recyclerViewCurrent.visibility == View.VISIBLE) {
                recyclerViewCurrent.visibility = View.GONE
                dropdownToday.text = "\u25BC"
            } else {
                recyclerViewCurrent.visibility = View.VISIBLE
                dropdownToday.text = "\u25B2"
            }
        }
        dropdownUpcoming = findViewById(R.id.dropDownUpcoming)
        dropdownUpcoming.setOnClickListener {
            if (recyclerViewUpcoming.visibility == View.VISIBLE) {
                recyclerViewUpcoming.visibility = View.GONE
                dropdownUpcoming.text = "\u25BC"
            } else {
                recyclerViewUpcoming.visibility = View.VISIBLE
                dropdownUpcoming.text = "\u25B2"
            }
        }
    }

    private fun observeDayList() {
        val dayListLiveData = myViewModel.getDayListLiveData()

        val observer = Observer<MutableList<Day>> { newList ->
            myViewModel.setDayList(newList)

            recyclerViewDays.adapter = MyDayAdapter(this, this, myViewModel)
        }

        //observe changes
        dayListLiveData.observe(this, observer)
    }

    private fun observeCurrentTitle() {
        val currentTitleLiveData = myViewModel.getCurrentTitleLiveData()

        val observer = Observer<String> { newTitle ->
            tvTodayWork.text = newTitle
        }

        currentTitleLiveData.observe(this, observer)
    }

    private fun observeWorkList() {
        val currentWorkListLiveData = myViewModel.getCurrentWorkListLiveData()
        val upcomingWorkListLiveData = myViewModel.getUpcomingWorkListLiveData()

        val observerCurrent = Observer<MutableList<Work>> { newList ->
            myViewModel.setCurrentWorkList(newList)
            recyclerViewCurrent.adapter = MyWorkListAdapter(
                this,
                this,
                myViewModel.getCurrentWorkList(),
                Constants.Companion.ViewDetailType.TODAY
            )
        }
        val observerUpcoming = Observer<MutableList<Work>> { newList ->
            myViewModel.setUpcomingWorkList(newList)
            recyclerViewUpcoming.adapter = MyWorkListAdapter(
                this,
                this,
                myViewModel.getUpcomingWorkList(),
                Constants.Companion.ViewDetailType.UPCOMING
            )
        }

        //observer will observe the list that is inside the value of its Livedata
        currentWorkListLiveData.observe(this, observerCurrent)
        upcomingWorkListLiveData.observe(this, observerUpcoming)
    }

    //implement the click listener of items
    override fun onItemDayClick(position: Int) {
        //set the selected day to change the background only
        myViewModel.selectDayAndDisplayWork(position)
    }

    override fun onItemWorkClick(position: Int, type: Constants.Companion.ViewDetailType) {
        Log.i(TAG, type.toString())
        val dialog =
            if (type == Constants.Companion.ViewDetailType.TODAY) DialogViewDetail(myViewModel.getCurrentWorkList()[position], myViewModel)
            else DialogViewDetail(myViewModel.getUpcomingWorkList()[position], myViewModel)
        dialog.show(supportFragmentManager, "detailToday")
    }

    override fun onItemWorkLongClick(position: Int, type: Constants.Companion.ViewDetailType) {
        val dialog =
            if (type == Constants.Companion.ViewDetailType.TODAY) DialogDelete(
                myViewModel.getCurrentWorkList()[position],
                myViewModel
            )
            else DialogDelete(myViewModel.getUpcomingWorkList()[position], myViewModel)
        dialog.show(supportFragmentManager, "dialog_delete")
    }
}