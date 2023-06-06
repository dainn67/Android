package com.example.foodbookingapp.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Appetizer
import com.example.foodbookingapp.model.DishesList

class AppetizerViewModel : ViewModel() {
    private var listAppetizer = DishesList.getAppetizerList()

    fun getList(): List<Appetizer> = listAppetizer

    fun addDishesToList(){
        listAppetizer.add(Appetizer(R.drawable.chicken_nugget, "Chicken nuggets", "10 delicious & scrumpy pieces of nuggets from the finest chicken in USA", 2.0))
        listAppetizer.add(Appetizer(R.drawable.french_fries, "French fries", "A box-full of delicious freshly fried potatoes", 2.5))
        listAppetizer.add(Appetizer(R.drawable.onion_ring, "Onion rings", "Fresh onions covered by crispy flour", 1.8))
        listAppetizer.add(Appetizer(R.drawable.fish_chip, "Fish & Chips", "Delicious traditional dished from the UK", 2.5))
    }

    fun moveToMainDish(view: View){
        Navigation.findNavController(view).navigate(R.id.action_appetizerFrag_to_mainDishFrag)
    }
}