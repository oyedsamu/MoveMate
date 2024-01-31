package com.byoyedele.movemate.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.byoyedele.movemate.ui.CalculateScreen
import com.byoyedele.movemate.ui.CostEstimateScreen
import com.byoyedele.movemate.ui.HomeScreen
import com.byoyedele.movemate.ui.SearchScreen
import com.byoyedele.movemate.ui.ShipmentScreen
import com.byoyedele.movemate.ui.theme.Purple
import com.byoyedele.movemate.ui.utils.bounceClick


@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(70.dp),
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        sealedValues<BottomNavItem>().sortedBy { it.id }.forEach { item ->
            val selected = currentRoute == item.route
            Box(modifier = Modifier
                .weight(1f)
                .selectable(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                )
                .bounceClick()
                .clickable {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                Column(verticalArrangement = Arrangement.Center) {
                    if (selected) {
                        Divider(color = Purple, thickness = 4.dp)
                    } else {
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = null,
                            tint = if (currentRoute == item.route) Purple else Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            item.label,
                            color = if (currentRoute == item.route) Purple else Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

enum class NavRoutes(val route: String) {
    COST_ESTIMATE_SCREEN("finalDestination"),
    SEARCH_SCREEN("search");
}

inline fun <reified T> sealedValues(): List<T> {
    return T::class.sealedSubclasses.mapNotNull { it.objectInstance as T }
}

@Composable
fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(
            BottomNavItem.Calculate.route,
        ) {
            CalculateScreen(navController = navController)
        }
        composable(
            BottomNavItem.Home.route,
            exitTransition = { fadeOut(tween(1000)) + slideOutVertically(tween(1000)) }) {
            HomeScreen(navController = navController)
        }
        composable(BottomNavItem.Shipment.route) {
            ShipmentScreen(navController = navController)
        }
        composable(BottomNavItem.Profile.route) { }
        composable(NavRoutes.COST_ESTIMATE_SCREEN.route) { CostEstimateScreen(navController) }
        composable(NavRoutes.SEARCH_SCREEN.route,
            enterTransition = { fadeIn(tween(2000)) + slideInVertically(tween(500)) }
        ) {
            SearchScreen(navController = navController)
        }
    }
}