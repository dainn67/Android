package com.example.foodbookingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.navigation.Navigation
import com.example.foodbookingapp.MainActivity.Companion.TAG
import com.example.foodbookingapp.MainActivity.Companion.listRestaurant
import com.example.foodbookingapp.MainActivity.Companion.restaurantId
import com.example.foodbookingapp.adapter.RestaurantListAdapter


class RestaurantFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_restaurant, container, false)

        val restaurantAdapter = RestaurantListAdapter(rootView.context, R.layout.item_restaurant, listRestaurant)
        rootView.findViewById<ListView>(R.id.lvRestaurant).adapter = restaurantAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ListView>(R.id.lvRestaurant).setOnItemClickListener{
                parent, view, position, id -> run {
                    restaurantId = position
                    Log.i(TAG, "RestaurantID: $restaurantId")
                    Navigation.findNavController(view).navigate(R.id.action_restaurantFrag_to_appetizerFrag)
                }
        }
    }
}