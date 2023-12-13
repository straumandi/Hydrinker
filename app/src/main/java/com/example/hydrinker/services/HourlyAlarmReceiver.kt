package com.example.hydrinker.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class HourlyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationService = NotificationService(context)
        notificationService.showBasicNotification()
    }
}