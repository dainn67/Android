package com.example.foodbookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodbookingapp.adapter.Appetizer
import com.example.foodbookingapp.adapter.Dessert
import com.example.foodbookingapp.adapter.MainDish
import com.example.foodbookingapp.adapter.Restaurant

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MyTag"
        var listRestaurant = mutableListOf<Restaurant>()
        var listAppetizer = mutableListOf<Appetizer>()
        var listMainDish = mutableListOf<MainDish>()
        var listDessert = mutableListOf<Dessert>()

        var restaurantId = -1
        var appetizerId = -1
        var mainDishId = -1
        var dessertId = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listRestaurant.add(Restaurant(R.drawable.kfc, "Kentucky fried chicken", "292 Bà Triệu, Lê Đại Hành, Hai Bà Trưng", 5))
        listRestaurant.add(Restaurant(R.drawable.domino, "Domino's pizza", "8 Tô Hiến Thành, Bùi Thị Xuân, Hai Ba Trưng", 4))
        listRestaurant.add(Restaurant(R.drawable.burger_king, "Burger King", "C4 Giảng Võ, Ba Đình, Hà Nội", 3))
        listRestaurant.add(Restaurant(R.drawable.subway, "Subway", "Đâu đó dưới lòng đất", 4))
        listRestaurant.add(Restaurant(R.drawable.li_quoc_su, "Phở 10 Lý Quốc Sư", "10 Lý Quốc Sư, Hoàn Kiếm, Hà Nội", 5))
        listRestaurant.add(Restaurant(R.drawable.mc_donald, "McDonald", "Century Tower, 458 Phố Minh Khai, Vĩnh Tuy, Hai Bà Trưng", 5))

        listAppetizer.add(Appetizer(R.drawable.chicken_nugget, "Chicken nuggets (10 pieces)", "10 delicious & scrumpy pieces of nuggets from the finest chicken in USA", 2.0))
        listAppetizer.add(Appetizer(R.drawable.french_fries, "French fries", "A box-full of delicious freshly fried potatoes", 2.5))
        listAppetizer.add(Appetizer(R.drawable.onion_ring, "Onion rings", "Fresh onions covered by crispy flour", 1.8))
        listAppetizer.add(Appetizer(R.drawable.fish_chip, "Fish & Chips", "delicious traditional dished from the UK", 2.5))
    }
}