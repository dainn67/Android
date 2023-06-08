package com.example.foodbookingapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Dish
import com.example.foodbookingapp.model.DishesList

class FragmentsViewModel : ViewModel() {
    private var listAppetizer = DishesList.getAppetizerList()
    private var listMainDish = DishesList.getMainDishList()
    private var listDessert = DishesList.getDessertList()

    private var listResult = DishesList.getResultList()

    private var appetizerOrder: MutableMap<Int, Int> = mutableMapOf()
    private var mainDishOrder: MutableMap<Int, Int> = mutableMapOf()
    private var dessertOrder: MutableMap<Int, Int> = mutableMapOf()


    fun getAppetizerList(): List<Dish> = listAppetizer
    fun getAppetizerOrderMap(): MutableMap<Int, Int> = appetizerOrder

    fun getDessertList(): List<Dish> = listDessert
    fun getDessertOrderMap(): MutableMap<Int, Int> = dessertOrder

    fun getMainDishesList(): List<Dish> = listMainDish
    fun getMainDishOrderMap(): MutableMap<Int, Int> = mainDishOrder

    fun addAppetizersToList() {
        listAppetizer.add(
            Dish(
                R.drawable.chicken_nugget,
                "Chicken nuggets",
                "10 delicious & scrumpy pieces of nuggets from the finest chicken in USA",
                2.0
            )
        )
        listAppetizer.add(
            Dish(
                R.drawable.french_fries,
                "French fries",
                "A box-full of delicious freshly fried potatoes",
                2.5
            )
        )
        listAppetizer.add(
            Dish(
                R.drawable.onion_ring,
                "Onion rings",
                "Fresh onions covered by crispy flour",
                1.8
            )
        )
        listAppetizer.add(
            Dish(
                R.drawable.fish_chip,
                "Fish & Chips",
                "Delicious traditional dished from the UK",
                2.5
            )
        )
    }

    fun addDessertsToList() {
        listDessert.add(
            Dish(
                R.drawable.ice_cream,
                "Ice cream",
                "Cold and fresh cup of ice cream",
                1.0
            )
        )
        listDessert.add(
            Dish(
                R.drawable.chocolate_cookies,
                "Chocolate cookies",
                "Baked cookies made from dark chocolate",
                1.0
            )
        )
        listDessert.add(
            Dish(
                R.drawable.jelly,
                "Jelly bowl",
                "Bouncing sweet bowl of jelly",
                2.5
            )
        )
        listDessert.add(
            Dish(
                R.drawable.tiramisu,
                "Tiramisu",
                "It's a Tiramisu cake",
                1.0
            )
        )
        listDessert.add(
            Dish(
                R.drawable.flan,
                "Flan cake",
                "A special cake made from flour with a top of melted caramel",
                2.0
            )
        )
    }

    fun addMainDishesToList() {
        listMainDish.add(
            Dish(
                R.drawable.fried_rice,
                "Spicy fried rice",
                "A wonderful mixture of fried rice, cheese, sausages and 11 exotic flavour",
                2.5
            )
        )
        listMainDish.add(
            Dish(
                R.drawable.drumsticks,
                "Fried chicken drumstick",
                "2 huge drumsticks covered with a heaven of flavour",
                2.0
            )
        )
        listMainDish.add(
            Dish(
                R.drawable.wings,
                "Chicken wings",
                "A set of 6 sweet & sour fried wings for the family",
                4.0
            )
        )
        listMainDish.add(
            Dish(
                R.drawable.hamburger,
                "Chicken hamburger",
                "A big size hamburger with chicken, bacon, salad and eggs",
                2.5
            )
        )
        listMainDish.add(
            Dish(
                R.drawable.burrito,
                "Burrito",
                "Traditional spicy delicious burrito from the one and only Mexico",
                2.5
            )
        )
    }

    fun addToResult() {
        for(id in listAppetizer.indices){
            if(listAppetizer[id] != null) listResult.add(listAppetizer[id])
        }
    }
}