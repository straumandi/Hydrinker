package com.example.hydrinker.services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.hydrinker.models.MeasurementUnit
import com.example.hydrinker.screens.SettingsData
import kotlinx.coroutines.flow.first

class SettingsService (private val dataStore: DataStore<Preferences>, private val context: Context) {
    private val notificationService = NotificationService(context)

    suspend fun saveSettings(settingsData: SettingsData) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.NOTIFICATIONS_ON] = settingsData.notificationsOn
            settings[PreferencesKeys.UNITS] = settingsData.units.name
        }

        if (settingsData.notificationsOn) {
            notificationService.scheduleHourlyNotifications()
        } else {
            notificationService.cancelScheduledNotifications()
        }
    }

    suspend fun readSettings(): SettingsData {
        val preferences = dataStore.data.first()
        return SettingsData(
            notificationsOn = preferences[PreferencesKeys.NOTIFICATIONS_ON] ?: false,
            units = MeasurementUnit.valueOf(preferences[PreferencesKeys.UNITS] ?: MeasurementUnit.UNKNOWN.name)
        )
    }
}