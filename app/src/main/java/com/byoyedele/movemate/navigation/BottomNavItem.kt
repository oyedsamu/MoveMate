package com.byoyedele.movemate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String, val id: Int) {
     data object Home : BottomNavItem("home", Icons.Outlined.Home, "Home", 0)
     data object Calculate : BottomNavItem("calculate", Icons.Default.AccountBox, "Calculate", 1)
     data object Shipment : BottomNavItem("shipment", Icons.Outlined.Refresh, "Shipment", 2)
     data object Profile : BottomNavItem("profile", Icons.Outlined.Person, "Profile", 3)
}
