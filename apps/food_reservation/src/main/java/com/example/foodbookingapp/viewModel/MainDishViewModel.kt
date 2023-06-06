package com.example.foodbookingapp.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.DishesList
import com.example.foodbookingapp.model.MainDish

class MainDishViewModel: ViewModel() {
    private var listMainDish = DishesList.getMainDishList()

    fun getList(): List<MainDish> = listMainDish

    fun addDishesToList(){
        listMainDish.add(MainDish(R.drawable.fried_rice, "Spicy fried rice", "A wonderful mixture of fried rice, cheese, sausages and 11 exotic flavour", 2.5))
        listMainDish.add(MainDish(R.drawable.drumsticks, "Fried chicken drumstick", "2 huge drumsticks covered with a heaven of flavour", 2.0))
        listMainDish.add(MainDish(R.drawable.wings, "Chicken wings", "A set of 6 sweet & sour fried wings for the family", 4.0))
        listMainDish.add(MainDish(R.drawable.hamburger, "Chicken hamburger", "A big size hamburger with chicken, bacon, salad and eggs", 2.5))
        listMainDish.add(MainDish(R.drawable.burrito, "Burrito", "Traditional spicy delicious burrito from the one and only Mexico", 2.5))
    }

    fun moveToDessert(view: View) = Navigation.findNavController(view).navigate(R.id.action_mainDishFrag_to_dessertFrag)

    fun moveToAppetizer(view: View) = Navigation.findNavController(view).navigate(R.id.action_mainDishFrag_to_appetizerFrag)
}