package com.example.foodbookingapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbookingapp.MainActivity.Companion.TAG
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Data
import com.example.foodbookingapp.model.Dish
import com.example.foodbookingapp.model.DishesList
import com.example.foodbookingapp.model.ResultDish

class FragmentsViewModel : ViewModel() {
    private var appetizerList: MutableList<Dish>
    private var mainDishList: MutableList<Dish>
    private var dessertList: MutableList<Dish>

    private var liveDataMapAppetizer: MutableLiveData<MutableMap<Int, Int>>
    private var liveDataMapMainDish: MutableLiveData<MutableMap<Int, Int>>
    private var liveDataMapDessert: MutableLiveData<MutableMap<Int, Int>>

    init {
        //initialize dishes to list and set all order amount to 0
        addAppetizersToList()
        addMainDishesToList()
        addDessertsToList()
        resetMap()

        //bind value of lists
        appetizerList = DishesList.getAppetizerList()
        mainDishList = DishesList.getMainDishList()
        dessertList = DishesList.getDessertList()

        //bind value if live data of maps
        liveDataMapAppetizer = MutableLiveData()
        liveDataMapAppetizer.value = DishesList.getMapAppetizer()

        liveDataMapMainDish = MutableLiveData()
        liveDataMapMainDish.value = DishesList.getMapMainDish()

        liveDataMapDessert = MutableLiveData()
        liveDataMapDessert.value = DishesList.getMapDessert()

    }

    //use for update dishes (manager)
    fun getAppetizerList() = appetizerList
    fun getMainDishList() = mainDishList
    fun getDessertList() = dessertList

    fun getLiveDataMapAppetizer() = liveDataMapAppetizer
    fun getLiveDataMapMainDish() = liveDataMapMainDish
    fun getLiveDataMapDessert() = liveDataMapDessert

    //initialize list items & maps
    private fun addAppetizersToList() {
        var tmpList = DishesList.getAppetizerList()
        tmpList.add(
            Dish(
                R.drawable.chicken_nugget,
                "Chicken nuggets",
                "10 delicious & scrumpy pieces of nuggets from the finest chicken in USA",
                2.0
            )
        )
        tmpList.add(
            Dish(
                R.drawable.french_fries,
                "French fries",
                "A box-full of delicious freshly fried potatoes",
                2.5
            )
        )
        tmpList.add(
            Dish(
                R.drawable.onion_ring,
                "Onion rings",
                "Fresh onions covered by crispy flour",
                1.8
            )
        )
        tmpList.add(
            Dish(
                R.drawable.fish_chip,
                "Fish & Chips",
                "Delicious traditional dished from the UK",
                2.5
            )
        )
    }

    private fun addDessertsToList() {
        var tmpList = DishesList.getDessertList()
        tmpList.add(
            Dish(
                R.drawable.ice_cream,
                "Ice cream",
                "Cold and fresh cup of ice cream",
                1.0
            )
        )
        tmpList.add(
            Dish(
                R.drawable.chocolate_cookies,
                "Chocolate cookies",
                "Baked cookies made from dark chocolate",
                1.0
            )
        )
        tmpList.add(
            Dish(
                R.drawable.jelly,
                "Jelly bowl",
                "Bouncing sweet bowl of jelly",
                2.5
            )
        )
        tmpList.add(
            Dish(
                R.drawable.tiramisu,
                "Tiramisu",
                "It's a Tiramisu cake",
                1.0
            )
        )
        tmpList.add(
            Dish(
                R.drawable.flan,
                "Flan cake",
                "A special cake made from flour with a top of melted caramel",
                2.0
            )
        )
    }

    private fun addMainDishesToList() {
        var tmpList = DishesList.getMainDishList()
        tmpList.add(
            Dish(
                R.drawable.fried_rice,
                "Spicy fried rice",
                "A wonderful mixture of fried rice, cheese, sausages and 11 exotic flavour",
                2.5
            )
        )
        tmpList.add(
            Dish(
                R.drawable.drumsticks,
                "Fried chicken drumstick",
                "2 huge drumsticks covered with a heaven of flavour",
                2.0
            )
        )
        tmpList.add(
            Dish(
                R.drawable.wings,
                "Chicken wings",
                "A set of 6 sweet & sour fried wings for the family",
                4.0
            )
        )
        tmpList.add(
            Dish(
                R.drawable.hamburger,
                "Chicken hamburger",
                "A big size hamburger with chicken, bacon, salad and eggs",
                2.5
            )
        )
        tmpList.add(
            Dish(
                R.drawable.burrito,
                "Burrito",
                "Traditional spicy delicious burrito from the one and only Mexico",
                2.5
            )
        )
    }

    private fun resetMap() {
        for (id in 0 until DishesList.getAppetizerList().size)
            DishesList.getMapAppetizer()[id] = 0

        for (id in 0 until DishesList.getMainDishList().size)
            DishesList.getMapMainDish()[id] = 0

        for (id in 0 until DishesList.getDessertList().size)
            DishesList.getMapDessert()[id] = 0
    }
}