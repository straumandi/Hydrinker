package com.example.hydrinker.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.hydrinker.screens.ProfileData
import kotlinx.coroutines.flow.first

class ProfileService(private val dataStore: DataStore<Preferences>) {

    private val NAME = stringPreferencesKey("name")
    private val WEIGHT = doublePreferencesKey("weight")
    private val AGE = intPreferencesKey("age")
    private val DAILY_GOAL = doublePreferencesKey("dailyGoal")
    private val DRINK_SIZE = doublePreferencesKey("drinkSize")

    suspend fun saveProfile(profileData: ProfileData) {
        dataStore.edit { profile ->
            profile[NAME] = profileData.name
            profile[WEIGHT] = profileData.weight
            profile[AGE] = profileData.age
            profile[DAILY_GOAL] = profileData.dailyGoal
            profile[DRINK_SIZE] = profileData.drinkSize
        }
    }

    suspend fun readProfile(): ProfileData {
        val preferences = dataStore.data.first()
        return ProfileData(
            name = preferences[NAME] ?: "",
            weight = preferences[WEIGHT] ?: 0.0,
            age = preferences[AGE] ?: 0,
            dailyGoal = preferences[DAILY_GOAL] ?: 0.0,
            drinkSize = preferences[DRINK_SIZE] ?: 0.0
        )
    }
}
