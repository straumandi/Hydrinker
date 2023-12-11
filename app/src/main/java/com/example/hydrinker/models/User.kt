package com.example.hydrinker.models

// User model to store user-related information
data class User(
    val name: String,
    val email: String,
    val gender: Gender,
    val age: Int,
    val weight: Double,
    val activityLevel: ActivityLevel,
    val dailyWaterGoal: Double,
    val notificationsEnabled: Boolean,
    val units: MeasurementUnit,
    // Add more optional fields for advanced settings if needed
)

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class ActivityLevel {
    SEDENTARY, LIGHT, MODERATE, ACTIVE, VERY_ACTIVE
}

enum class MeasurementUnit {
    MILLILITERS, OUNCES, UNKNOWN
}