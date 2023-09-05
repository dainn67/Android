package com.example.retrofitapplication.model.location

import java.io.Serializable

class Location(
    private val street: Street,
    private val city: String,
    private val state: String,
    private val country: String,
    private val postcode: Int,
    private val coordinates: Coordinates,
    private val timezone: Timezone
): Serializable {
}