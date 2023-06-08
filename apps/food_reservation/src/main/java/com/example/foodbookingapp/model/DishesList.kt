package com.example.foodbookingapp.model

class DishesList {
    companion object{
        private var listAppetizer = mutableListOf<Dish>()
        private var listMainDish = mutableListOf<Dish>()
        private var listDessert = mutableListOf<Dish>()

        private var listResult = mutableListOf<Dish>()

        fun getAppetizerList(): MutableList<Dish> = listAppetizer
        fun getMainDishList(): MutableList<Dish> = listMainDish
        fun getDessertList(): MutableList<Dish> = listDessert
        fun getResultList(): MutableList<Dish> = listResult
    }
}