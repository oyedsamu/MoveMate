package com.byoyedele.movemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byoyedele.movemate.navigation.BottomNavItem
import com.byoyedele.movemate.navigation.BottomNavigationBar
import com.byoyedele.movemate.navigation.NavigationHost
import com.byoyedele.movemate.ui.theme.MoveMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveMateTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute= navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == BottomNavItem.Home.route){
                AnimatedVisibility(visible = currentRoute == BottomNavItem.Home.route, enter = slideInHorizontally() + fadeIn(), exit = slideOutVertically() + fadeOut()) {
                    BottomNavigationBar(navController)
                }
            }
        },
    ) { paddingValues ->
        NavigationHost(navController = navController, paddingValues)
    }
}