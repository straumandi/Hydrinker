package com.example.hydrinker.services

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val NAME = stringPreferencesKey("name")
    val WEIGHT = doublePreferencesKey("weight")
    val AGE = intPreferencesKey("age")
    val DAILY_GOAL = doublePreferencesKey("dailyGoal")
    val DRINK_SIZE = doublePreferencesKey("drinkSize")
    val NOTIFICATIONS_ON = booleanPreferencesKey("notificationsOn")
    val UNITS = stringPreferencesKey("units")
    val EMAIL = stringPreferencesKey("email")
}