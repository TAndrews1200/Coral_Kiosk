package com.coral.coral_kiosk

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoralApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }


    /**
     * Creates the notification channel, primarily for when an item is added to the cart
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name"
            val description = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CART_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = ContextCompat.getSystemService(
                this, NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    companion object{
        @JvmStatic
        val CART_CHANNEL_ID = "CORAL_NOTIFICATION_CHANNEL"
        @JvmStatic
        val CART_NOTIFICATION_ID = 450
    }
}