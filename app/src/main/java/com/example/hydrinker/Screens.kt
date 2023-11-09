package com.example.hydrinker

/*
 * A sealed class is a special type of class in Kotlin
 * that is used to represent a restricted hierarchy of classes,
 * where each subclass is known and predefined.
 */
sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Profile : Screens("profile_route")
    object Score : Screens("score_route")
    object History : Screens("history_route")
    object Settings : Screens("settings_route")
}