package com.example.foodbookingapp.model

open class Dish(
    private val img: Int,
    private val name: String,
    private val desc: String,
    private val price: Double
) {
    fun getImg() = img
    fun getName() = name
    fun getDesc() = desc
    fun getPrice() = price
}

class Appetizer(
    img: Int,
    name: String,
    desc: String,
    price: Double
) : Dish(img, name, desc, price) {

}

class MainDish(
    img: Int,
    name: String,
    desc: String,
    price: Double
) : Dish(img, name, desc, price) {

}

class Dessert(
    img: Int,
    name: String,
    desc: String,
    price: Double
) : Dish(img, name, desc, price) {

}