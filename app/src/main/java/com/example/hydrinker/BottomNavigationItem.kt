package com.example.hydrinker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

/*
 * A data class is a special type of class
 * that is primarily used to hold data,
 * representing simple, immutable data structures.
 */
data class BottomNavigationItem(
    val title: String = "",
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Outlined.Home,
    val route : String = "",
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screens.Home.route,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Profile",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = Screens.Profile.route,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Score",
                selectedIcon = Icons.Filled.Star,
                unselectedIcon = Icons.Outlined.Star,
                route = Screens.Score.route,
                hasNews = false
            ),
            BottomNavigationItem(
                title = "History",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
                route = Screens.History.route,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = Screens.Settings.route,
                hasNews = false,
            )
        )
    }
}

