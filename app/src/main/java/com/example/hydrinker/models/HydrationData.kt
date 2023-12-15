package com.example.hydrinker.models

import java.util.Date

// Hydration data model for tracking water intake
data class HydrationData(
    val date: Date,  // Timestamp for the hydration record
    val amount: Double,  // Amount of water consumed in milliliters
)