package com.example.hydrinker.models

// Hydration data model for tracking water intake
data class HydrationData(
    val date: Long,  // Timestamp for the hydration record
    val amount: Double,  // Amount of water consumed in milliliters
)