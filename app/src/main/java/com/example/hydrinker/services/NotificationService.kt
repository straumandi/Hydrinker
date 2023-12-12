package com.example.hydrinker.services

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.hydrinker.Hydrinker
import com.example.hydrinker.R
import kotlinx.coroutines.flow.first
import kotlin.random.Random

class NotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    // TODO: REPLACE WITH MORE APPEALING INFO
    fun showBasicNotification() {
        val notification = NotificationCompat.Builder(context, "water_notification")
            .setContentTitle("Water Reminder")
            .setContentText("Time to drink a glass of water")
            .setSmallIcon(R.drawable.water_drop)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun scheduleHourlyNotifications() {

        if (!isAlarmSet()) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, HourlyAlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            val startTime = System.currentTimeMillis() // Start at current time
            val intervalTime = (60 * 60 * 1000).toLong() // 60 minutes in milliseconds

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                startTime,
                intervalTime,
                pendingIntent
            )
        }
    }

    fun cancelScheduledNotifications() {
        val intent = Intent(context, HourlyAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(it)
        }
    }

    private fun isAlarmSet(): Boolean {
        val intent = Intent(context, HourlyAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent != null
    }

    suspend fun isNotificationEnabled(dataStore: DataStore<Preferences>): Boolean {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.NOTIFICATIONS_ON] ?: true // default to true if not set
    }
}