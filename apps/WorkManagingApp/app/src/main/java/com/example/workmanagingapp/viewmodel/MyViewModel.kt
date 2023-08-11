package com.example.workmanagingapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workmanagingapp.model.Data
import com.example.workmanagingapp.model.Work
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class MyViewModel: ViewModel() {
    private val data = Data()

    //get the list and use livedata to update
    private var workList = data.getWorkList()
    private val dayList = data.getDayList()
    private var workListLiveData = MutableLiveData<MutableList<Work>>()

    //getters
    fun getWorkList() = workList
    fun getDayList() = dayList
    fun setWorkList(list: MutableList<Work>) {workList = list}
    fun getWorkListLiveData() = workListLiveData

    //livedata needs to be initialized and assigned its value
    init {
        workListLiveData = MutableLiveData()
        workListLiveData.value = workList
    }

    fun addSampleWorks(){
        workList.add(Work("Go home 1", Date(), "kaerimashou 1"))
        workList.add(Work("Go home 2", Date(), "kaerimashou 2"))
        workList.add(Work("Go home 3", Date(), "kaerimashou 3"))
        workList.add(Work("Go home 4", Date(), "kaerimashou 4"))
        workList.add(Work("Go home 5", Date(), "kaerimashou 5"))

        workListLiveData.value = workList
    }
}