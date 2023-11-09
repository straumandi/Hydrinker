package com.example.hydrinker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.screens.HistoryScreen
import com.example.hydrinker.screens.HomeScreen
import com.example.hydrinker.screens.ProfileScreen
import com.example.hydrinker.screens.ScoreScreen
import com.example.hydrinker.screens.SettingsScreen
import com.example.hydrinker.ui.theme.HydrinkerTheme

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

    // scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
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
                                    } else if (navigationItem.hasNews){
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector =
                                    if(index == selectedItemIndex) {
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
                                restoreState = true
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
            modifier = Modifier.padding(paddingValues = paddingValues)) {
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