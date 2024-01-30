package com.byoyedele.movemate.viewmodels

import androidx.lifecycle.ViewModel
import com.byoyedele.movemate.data.ShipmentStatus
import com.byoyedele.movemate.data.ShipmentStatusLabel
import com.byoyedele.movemate.data.listOfShipmentStatus

class ShipmentViewModel : ViewModel() {
    fun getUpdatedStatusLabelList(selectedId: Int): List<ShipmentStatus> {
        val selectedStatusLabel =
            ShipmentStatusLabel.entries.find { label -> label.id == selectedId }
                ?: ShipmentStatusLabel.ALL

        return if (selectedStatusLabel == ShipmentStatusLabel.ALL)
            listOfShipmentStatus
        else
            listOfShipmentStatus.filter { it.status == selectedStatusLabel }
    }
}