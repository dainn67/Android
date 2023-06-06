package com.example.foodbookingapp.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.foodbookingapp.R
import com.example.foodbookingapp.model.Dessert
import com.example.foodbookingapp.model.DishesList

class DessertViewModel: ViewModel() {
    private var  listDessert = DishesList.getDessertList()

    fun getList(): List<Dessert> = listDessert

    fun addDishesToList(){
        listDessert.add(Dessert(R.drawable.ice_cream, "Ice cream", "Cold and fresh cup of ice cream", 1.0))
        listDessert.add(Dessert(R.drawable.chocolate_cookies, "Chocolate cookies", "Baked cookies made from dark chocolate", 1.0))
        listDessert.add(Dessert(R.drawable.jelly, "Jelly bowl", "Bouncing sweet bowl of jelly", 2.5))
        listDessert.add(Dessert(R.drawable.tiramisu, "Tiramisu", "It's a Tiramisu cake", 1.0))
        listDessert.add(Dessert(R.drawable.flan, "Flan cake", "A special cake made from flour with a top of melted caramel", 2.0))
    }

    fun moveToResult(view: View){
        Navigation.findNavController(view).navigate(R.id.action_dessertFrag_to_resultFrag)
    }

    fun moveToMainDish(view: View){
        Navigation.findNavController(view).navigate(R.id.action_dessertFrag_to_mainDishFrag)
    }
}