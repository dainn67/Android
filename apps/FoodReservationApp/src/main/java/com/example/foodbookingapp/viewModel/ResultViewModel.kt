package com.example.foodbookingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbookingapp.model.Data
import com.example.foodbookingapp.model.DishesList
import com.example.foodbookingapp.model.ResultDish

class ResultViewModel : ViewModel() {
    //list to display on result page & total cost
    private var resultList = DishesList.getResultList()
    private var totalCost = Data.totalCost

    //live data
    private var myLiveData = MutableLiveData<List<ResultDish>>()
    private var myLiveDataTotalCost = MutableLiveData<Double>()

    init {
        myLiveData.value = resultList
        myLiveDataTotalCost.value = totalCost
    }

    fun getListResult(): List<ResultDish> = resultList

    fun getLiveData(): MutableLiveData<List<ResultDish>> = myLiveData
    fun getLiveDataTotalCost(): MutableLiveData<Double> = myLiveDataTotalCost

    fun addToResult(){
        //if user comeback to order more, list must be cleared to be added again
        resultList.clear()

        for (index in DishesList.getAppetizerList().indices){
            if(DishesList.getMapAppetizer()[index] != 0){
                val myDish = DishesList.getAppetizerList()[index]
                val amount = DishesList.getMapAppetizer()[index] ?: 0

                resultList.add(ResultDish(myDish.getImg(), myDish.getName(), myDish.getDesc(), myDish.getPrice(), amount))
                totalCost += myDish.getPrice() * amount
            }
        }
        for (index in DishesList.getMainDishList().indices){
            if(DishesList.getMapMainDish()[index] != 0){
                val myDish = DishesList.getMainDishList()[index]
                val amount = DishesList.getMapMainDish()[index] ?: 0

                resultList.add(ResultDish(myDish.getImg(), myDish.getName(), myDish.getDesc(), myDish.getPrice(), amount))
                totalCost += myDish.getPrice() * amount
            }
        }
        for (index in DishesList.getDessertList().indices){
            if(DishesList.getMapDessert()[index] != 0){
                val myDish = DishesList.getDessertList()[index]
                val amount = DishesList.getMapDessert()[index] ?: 0

                resultList.add(ResultDish(myDish.getImg(), myDish.getName(), myDish.getDesc(), myDish.getPrice(), amount))
                totalCost += myDish.getPrice() * amount
            }
        }

        //notify changes
        myLiveData.value = resultList
        myLiveDataTotalCost.value = totalCost
    }
}