package com.example.foodbookingapp.model

open class Dish(
    private val img: Int,
    private val name: String,
    private val desc: String,
    private val price: Double,
) {
    fun getImg() = img
    fun getName() = name
    fun getDesc() = desc
    fun getPrice() = price
}

open class ResultDish(
    img: Int,
    name: String,
    desc: String,
    price: Double,
    amount: Int
): Dish(img, name, desc, price){
    private val amount = amount

    fun getAmount() = amount
}