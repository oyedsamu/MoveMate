package com.byoyedele.movemate.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.byoyedele.movemate.ui.theme.Green
import com.byoyedele.movemate.ui.theme.Orange

enum class ShipmentStatusLabel(
    val id: Int,
    val title: String,
    val tag: String,
    val icon: ImageVector,
    val color: Color,
) {
    ALL(0, "All", "all", Icons.Default.Add, Color.Magenta),
    COMPLETED(1, "Completed", "completed", Icons.Default.Check, Green),
    PENDING_ORDER(2, "Pending order", "pending", Icons.Default.Face, Orange),
    IN_PROGRESS(3, "In-progress", "in-progress", Icons.Default.ShoppingCart, Green),
    LOADING(4, "Loading", "loading", Icons.Default.AddCircle, Color.Blue),
    CANCELLED(5, "Cancelled", "cancelled", Icons.Default.Clear, Color.Red),
}

