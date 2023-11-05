package com.example.hydrinker.models

// Achievement model to represent user achievements
data class Achievement(
    val name: String,  // Achievement name
    val icon: Int,  // Resource ID of the achievement icon
    val condition: AchievementCondition,
)