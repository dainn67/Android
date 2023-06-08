package com.example.foodbookingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Dish
import com.example.foodbookingapp.model.DishesList

class ResultViewModel : ViewModel() {
    private var resultList = DishesList.getResultList()
    private var myLiveData = MutableLiveData<List<Dish>>()

    fun getListResult(): List<Dish> = resultList

    fun addDummyData() {
        resultList.add(Dish(R.drawable.ice_cream, "アイスクリーム", "とても　おいしい　アイスクリーム　よ", 1.0))
        resultList.add(Dish(R.drawable.tiramisu, "チラミス", "とても　おいしい　チラミス　よ", 2.0))
        myLiveData.value = resultList
    }

    fun initData() {
        addDummyData()
        myLiveData.value = resultList
    }

    fun getLiveData(): MutableLiveData<List<Dish>> = myLiveData
}