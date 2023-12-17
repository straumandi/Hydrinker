package com.example.hydrinker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.screens.HistoryScreen
import com.example.hydrinker.screens.HomeScreen
import com.example.hydrinker.screens.ProfileScreen
import com.example.hydrinker.screens.ScoreScreen
import com.example.hydrinker.screens.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    // initializing the default selected item
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Update selectedItemIndex based on the destination route
            when (destination.route) {
                Screens.Home.route -> selectedItemIndex = 0
                Screens.Profile.route -> selectedItemIndex = 1
                Screens.Score.route -> selectedItemIndex = 2
                Screens.History.route -> selectedItemIndex = 3
                Screens.Settings.route -> selectedItemIndex = 4
            }
        }
    }

    // scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color(0xFF004FAB)),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            // if our current index of the itemList is equal to the index of navigationItem
                            // then set it to selected = true
                            selected = index == selectedItemIndex,
                            label = {
                                Text(navigationItem.title)
                            },
                            //alwaysShowLabel = false, --> shows only the label for the selected item
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (navigationItem.badgeCount !== null) {
                                            Badge {
                                                Text(text = navigationItem.badgeCount.toString())
                                            }
                                        } else if (navigationItem.hasNews) {
                                            Badge()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector =
                                        if (index == selectedItemIndex) {
                                            navigationItem.selectedIcon
                                        } else navigationItem.unselectedIcon,
                                        contentDescription = navigationItem.title
                                    )
                                }
                            },
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screens.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(Screens.Score.route) {
                ScoreScreen(navController = navController)
            }
            composable(Screens.History.route) {
                HistoryScreen(navController = navController)
            }
            composable(Screens.Settings.route) {
                SettingsScreen(navController = navController)
            }
        }
    }
}