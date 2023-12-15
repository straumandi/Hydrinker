package com.example.hydrinker.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.hydrinker.screens.ProfileData
import kotlinx.coroutines.flow.first

class ProfileService(private val dataStore: DataStore<Preferences>) {

    suspend fun saveProfile(profileData: ProfileData) {
        dataStore.edit { profile ->
            profile[PreferencesKeys.NAME] = profileData.name
            profile[PreferencesKeys.WEIGHT] = profileData.weight
            profile[PreferencesKeys.AGE] = profileData.age
            profile[PreferencesKeys.DAILY_GOAL] = profileData.dailyGoal
            profile[PreferencesKeys.DRINK_SIZE] = profileData.drinkSize
        }
    }

    suspend fun readProfile(): ProfileData {
        val preferences = dataStore.data.first()
        val data = ProfileData(
            name = preferences[PreferencesKeys.NAME] ?: "",
            weight = preferences[PreferencesKeys.WEIGHT] ?: 0.0,
            age = preferences[PreferencesKeys.AGE] ?: 0,
            dailyGoal = preferences[PreferencesKeys.DAILY_GOAL] ?: 0,
            drinkSize = preferences[PreferencesKeys.DRINK_SIZE] ?: 0
        )
        println(data)
        return ProfileData(
            name = preferences[PreferencesKeys.NAME] ?: "",
            weight = preferences[PreferencesKeys.WEIGHT] ?: 0.0,
            age = preferences[PreferencesKeys.AGE] ?: 0,
            dailyGoal = preferences[PreferencesKeys.DAILY_GOAL] ?: 0,
            drinkSize = preferences[PreferencesKeys.DRINK_SIZE] ?: 0
        )
    }
}
