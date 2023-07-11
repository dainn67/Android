package com.example.foodbookingapp.model

import androidx.lifecycle.MutableLiveData

class DishesList {
    companion object{

        //list of dishes to display
        private var listAppetizer = mutableListOf<Dish>()
        private var listMainDish = mutableListOf<Dish>()
        private var listDessert = mutableListOf<Dish>()
        private var listResult = mutableListOf<ResultDish>()

        private var mapAppetizer = mutableMapOf<Int, Int>()
        private var mapMainDish = mutableMapOf<Int, Int>()
        private var mapDessert = mutableMapOf<Int, Int>()
        private var mapResult = mutableMapOf<Int, Int>()

        fun getAppetizerList() = listAppetizer
        fun getMainDishList() = listMainDish
        fun getDessertList() = listDessert
        fun getResultList() = listResult

        fun getMapAppetizer() = mapAppetizer
        fun getMapMainDish() = mapMainDish
        fun getMapDessert() = mapDessert
        fun getMapResult() = mapResult
    }

}