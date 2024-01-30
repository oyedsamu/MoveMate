package com.byoyedele.movemate.data

import androidx.annotation.DrawableRes
import com.byoyedele.movemate.R

data class VehicleDetails(
    val name: String,
    val type: String,
    @DrawableRes
    val image: Int
)

val listOfVehicles = listOf(
    VehicleDetails(name = "Ocean freight", type = "International", R.drawable.ship),
    VehicleDetails(name = "Cargo freight", type = "Reliable", R.drawable.truck),
    VehicleDetails(name = "Air freight", type = "International", R.drawable.stacked_box),
    VehicleDetails(name = "Land freight", type = "Reliable", R.drawable.truck),
    VehicleDetails(name = "Water freight", type = "Dependable", R.drawable.ship),
)
