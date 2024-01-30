package com.byoyedele.movemate.model

import androidx.annotation.DrawableRes
import com.byoyedele.movemate.R

data class VehicleDetails(
    val name: String,
    val type: String,
    @DrawableRes
    val image: Int
)

