package com.byoyedele.movemate.model

data class ShipmentStatus(
    val status: ShipmentStatusLabel,
    val message: String,
    val details: String,
    val amount: String,
    val date: String,
)

