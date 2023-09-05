package com.example.retrofitapplication.model.user

import com.example.retrofitapplication.model.location.Location
import java.io.Serializable

class User(
    private val gender: String,
    private val name: Name,
    private val location: Location,
    private val email: String,
    private val login: Login,
    private val dob: CustomDate,
    private val registered: CustomDate,
    private val phone: String,
    private val cell: String,
    private val id: ID,
    private val picture: Picture,
    private val nat: String,
): Serializable {

}