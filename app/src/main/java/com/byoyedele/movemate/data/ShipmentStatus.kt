package com.byoyedele.movemate.data

data class ShipmentStatus(
    val status: ShipmentStatusLabel,
    val message: String,
    val details: String,
    val amount: String,
    val date: String,
)

val listOfShipmentStatus = listOf(
    ShipmentStatus(
        status = ShipmentStatusLabel.IN_PROGRESS,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023",
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.PENDING_ORDER,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.IN_PROGRESS,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.PENDING_ORDER,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.COMPLETED,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.PENDING_ORDER,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.CANCELLED,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.LOADING,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.PENDING_ORDER,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
    ShipmentStatus(
        status = ShipmentStatusLabel.COMPLETED,
        message = "Arriving today!",
        details = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today",
        amount = "$1400 USD",
        date = "Sep 20, 2023"
    ),
)