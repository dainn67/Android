package com.example.foodbookingapp.model

class DishesList {
    companion object{
        private var listAppetizer = mutableListOf<Appetizer>()
        private var listMainDish = mutableListOf<MainDish>()
        private var listDessert = mutableListOf<Dessert>()

        private var listResult = mutableListOf<Dish>()

        fun getAppetizerList(): MutableList<Appetizer> = listAppetizer
        fun getMainDishList(): MutableList<MainDish> = listMainDish
        fun getDessertList(): MutableList<Dessert> = listDessert
    }
}