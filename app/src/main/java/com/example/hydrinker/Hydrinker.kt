package com.example.hydrinker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.hydrinker.services.NotificationService
import kotlinx.coroutines.launch

class Hydrinker : Application() {
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var notificationService: NotificationService

    // Override onCreate() to register an AppLifecycleObserver
    override fun onCreate() {
        super.onCreate()
        notificationService = NotificationService(this)

        createNotificationChannel()
        scheduleOrCancelNotifications()
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            "water_notification",
            "Water",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun scheduleOrCancelNotifications() {
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            if (notificationService.isNotificationEnabled(dataStore)) {
                notificationService.scheduleHourlyNotifications()
            } else {
                notificationService.cancelScheduledNotifications()
            }
        }
    }
}