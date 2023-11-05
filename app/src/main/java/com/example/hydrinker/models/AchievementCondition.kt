package com.example.hydrinker.models

data class AchievementCondition(
    val targetType: AchievementTargetType, // Type of target (e.g., "drink", "workout")
    val targetAmount: Int,  // Target amount to achieve the condition
)

enum class AchievementTargetType {
    DRINK, WORKOUT
}