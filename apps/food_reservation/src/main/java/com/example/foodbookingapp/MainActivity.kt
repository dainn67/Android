package com.example.foodbookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodbookingapp.adapter.Appetizer
import com.example.foodbookingapp.adapter.Dessert
import com.example.foodbookingapp.adapter.MainDish

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MyTag"
        var listAppetizer = mutableListOf<Appetizer>()
        var listMainDish = mutableListOf<MainDish>()
        var listDessert = mutableListOf<Dessert>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAppetizer.add(Appetizer(R.drawable.chicken_nugget, "Chicken nuggets", "10 delicious & scrumpy pieces of nuggets from the finest chicken in USA", 2.0))
        listAppetizer.add(Appetizer(R.drawable.french_fries, "French fries", "A box-full of delicious freshly fried potatoes", 2.5))
        listAppetizer.add(Appetizer(R.drawable.onion_ring, "Onion rings", "Fresh onions covered by crispy flour", 1.8))
        listAppetizer.add(Appetizer(R.drawable.fish_chip, "Fish & Chips", "Delicious traditional dished from the UK", 2.5))

        listMainDish.add(MainDish(R.drawable.fried_rice, "Spicy fried rice", "A wonderful mixture of fried rice, cheese, sausages and 11 exotic flavour", 2.5))
        listMainDish.add(MainDish(R.drawable.drumsticks, "Fried chicken drumstick", "2 huge drumsticks covered with a heaven of flavour", 2.0))
        listMainDish.add(MainDish(R.drawable.wings, "Chicken wings", "A set of 6 sweet & sour fried wings for the family", 4.0))
        listMainDish.add(MainDish(R.drawable.hamburger, "Chicken hamburger", "A big size hamburger with chicken, bacon, salad and eggs", 2.5))
        listMainDish.add(MainDish(R.drawable.burrito, "Burrito", "Traditional spicy delicious burrito from the one and only Mexico", 2.5))

        listDessert.add(Dessert(R.drawable.ice_cream, "Ice cream", "Cold and fresh cup of ice cream", 1.0))
        listDessert.add(Dessert(R.drawable.chocolate_cookies, "Chocolate cookies", "Baked cookies made from dark chocolate", 1.0))
        listDessert.add(Dessert(R.drawable.jelly, "Jelly bowl", "Bouncing sweet bowl of jelly", 2.5))
        listDessert.add(Dessert(R.drawable.tiramisu, "Tiramisu", "It's a Tiramisu cake", 1.0))
        listDessert.add(Dessert(R.drawable.flan, "Flan cake", "A special cake made from flour with a top of melted caramel", 2.0))
    }
}