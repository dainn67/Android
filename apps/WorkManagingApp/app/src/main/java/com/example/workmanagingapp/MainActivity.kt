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

        //TODO: load data from SQLite using content provider here
        myViewModel.loadWorkList()

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
        observeWorkList()
    }

    private fun setRecyclerViews() {
        recyclerViewDays = findViewById(R.id.recViewDays)
        recyclerViewDays.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDays.adapter = MyDayAdapter(this,this, myViewModel)

        recyclerViewCurrent = findViewById(R.id.recViewCurrent)
        recyclerViewCurrent.layoutManager = LinearLayoutManager(this)
        recyclerViewCurrent.adapter = MyWorkListAdapter(this, this, myViewModel.getWorkList(), Constants.Companion.ViewDetailType.TODAY)

        recyclerViewUpcoming = findViewById(R.id.recViewUpcoming)
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(this)
        recyclerViewUpcoming.adapter = MyWorkListAdapter(this, this, myViewModel.getWorkList(), Constants.Companion.ViewDetailType.UPCOMING)
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

    private fun observeDayList(){
        val dayListLiveData = myViewModel.getDayListLiveData()

        val observer = Observer<MutableList<Day>> {newList ->
            myViewModel.setDayList(newList)

            recyclerViewDays.adapter = MyDayAdapter(this, this, myViewModel)
        }

        //observe changes
        dayListLiveData.observe(this, observer)
    }
    private fun observeWorkList() {
        val workListLiveData = myViewModel.getWorkListLiveData()

        val observer = Observer<MutableList<Work>> { newList ->
            //update the list
            myViewModel.setWorkList(newList)

            //reset the adapter
            recyclerViewCurrent.adapter = MyWorkListAdapter(this, this, myViewModel.getWorkList(), Constants.Companion.ViewDetailType.TODAY)
        }

        //observer will observe the list that is inside the value of its Livedata
        workListLiveData.observe(this, observer)
    }

    override fun onItemDayClick(position: Int) {
        Log.i(TAG, "Pressed ${myViewModel.getDayList()[position]}")

        //set the selected day to change the background only
        myViewModel.selectDay(position)
    }

    override fun onItemWorkClick(position: Int) {
        Log.i(TAG, "Pressed ${myViewModel.getWorkList()[position]}")
        val dialog = DialogViewDetail(myViewModel.getWorkList()[position])
        dialog.show(supportFragmentManager, "detailToday")
    }

    override fun onItemLongClick(position: Int) {
        Log.i(TAG, "Long pressed ${myViewModel.getWorkList()[position]}")
        val dialog = DialogDelete(myViewModel.getWorkList()[position])
        dialog.show(supportFragmentManager, "dialog_delete")
    }
}